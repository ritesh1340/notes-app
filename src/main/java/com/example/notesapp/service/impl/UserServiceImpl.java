package com.example.notesapp.service.impl;

import com.example.notesapp.dao.UserDao;
import com.example.notesapp.request.User;
import exception.UserMismatchException;
import exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class UserServiceImpl {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public CompletionStage<User> get(String userID) {
        return userDao.get(userID)
            .thenApply(userOptional -> userOptional.orElseThrow(() -> new UserNotFoundException(userID)));
    }

    public CompletionStage<User> create(User user) {
        return userDao.get(user.getUserID())
            .thenCompose(userOptional -> userOptional
                .map(existingUser -> checkIdempotency(user, existingUser))
                .orElseGet(() -> userDao.create(user).toCompletableFuture()));
    }

    public CompletionStage<User> update(User user) {
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
