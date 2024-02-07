package com.example.notesapp.service.impl;

import com.example.notesapp.dao.UserDao;
import com.example.notesapp.request.User;
import exception.UserCredentialsMismatchException;
import exception.UserMismatchException;
import exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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

    public CompletionStage<String> getToken(User user) {
        return get(user.getUserID())
            .thenApply(existingUser -> checkPasswordAndGenerateToken(user, existingUser));
    }

    private String checkPasswordAndGenerateToken(User user, User existingUser) {
        if (existingUser.getPassword().equals(user.getPassword())) {
            return tokenGenerationService.generateToken(user.getUserID());
        }
        throw new UserCredentialsMismatchException(user.getUserID());
    }

    private CompletionStage<User> get(String userID) {
        return userDao.get(userID)
            .thenApply(userOptional -> userOptional.orElseThrow(() -> new UserNotFoundException(userID)));
    }

    private CompletionStage<User> update(User user) {
        return get(user.getUserID())
            .thenCompose(__ -> userDao.update(user));
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
