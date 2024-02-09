package com.example.notesapp.service.impl;

import com.example.notesapp.dao.UserDao;
import com.example.notesapp.dto.Token;
import com.example.notesapp.exception.InvalidTokenException;
import com.example.notesapp.exception.UserCredentialsMismatchException;
import com.example.notesapp.exception.UserMismatchException;
import com.example.notesapp.exception.UserNotFoundException;
import com.example.notesapp.request.User;
import com.example.notesapp.response.SignInResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.time.Instant.now;

@Component
public class UserServiceImpl {

    private final UserDao userDao;
    private final TokenGenerationServiceImpl tokenGenerationService;

    public UserServiceImpl(UserDao userDao, TokenGenerationServiceImpl tokenGenerationService) {
        this.userDao = userDao;
        this.tokenGenerationService = tokenGenerationService;
    }

    public CompletionStage<User> create(User user) {
        return userDao.get(user.getUserID())
            .thenCompose(userOptional -> userOptional
                .map(existingUser -> checkIdempotency(user, existingUser))
                .orElseGet(() -> userDao.create(user).toCompletableFuture()));
    }

    public CompletionStage<SignInResponse> getToken(User user) {
        return get(user.getUserID())
            .thenCompose(existingUser -> {
                try {
                    return checkPasswordAndGenerateToken(user, existingUser);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public CompletionStage<User> update(User user, String token) throws IOException {
        return validateToken(user.getUserID(), token).thenCompose(__ -> userDao.update(user));
    }

    public CompletionStage<Boolean> validateToken(String userID, String token) throws IOException {
        return get(userID).
            thenCombine(tokenGenerationService.getDecryptedToken(token), (user, decryptedToken) -> {
                if (!isSafeToAuthenticate(user, decryptedToken)) {
                    throw new InvalidTokenException(userID);
                }
                return Boolean.TRUE;
        });
    }

    private boolean isSafeToAuthenticate(User user, Token decryptedToken) {
        return user.getUserID().equals(decryptedToken.getUserID()) &&
            user.getPassword().equals(decryptedToken.getUserPassword()) &&
            Instant.parse(decryptedToken.getExpiry()).isAfter(now());
    }

    private CompletionStage<SignInResponse> checkPasswordAndGenerateToken(User user, User existingUser) throws JsonProcessingException {
        if (existingUser.getPassword().equals(user.getPassword())) {
            return tokenGenerationService.generateToken(user.getUserID(), user.getPassword());
        }
        throw new UserCredentialsMismatchException(user.getUserID());
    }

    private CompletionStage<User> get(String userID) {
        return userDao.get(userID)
            .thenApply(userOptional -> userOptional.orElseThrow(() -> new UserNotFoundException(userID)));
    }

    private CompletableFuture<User> checkIdempotency(User user, User existingUser) {
        if (isCurrentAndExistingUserMatching(user, existingUser)) {
            return CompletableFuture.completedFuture(existingUser);
        }
        throw new UserMismatchException(user.getName(), existingUser.getName());
    }

    private boolean isCurrentAndExistingUserMatching(User user, User existingUser) {
        return user.getName().equals(existingUser.getName());
    }
}
