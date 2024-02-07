package com.example.notesapp.dao;

import com.example.notesapp.request.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserDao {

    public Map<String, User> users = new HashMap<>();

    public CompletionStage<Optional<User>> get(String userID) {
        return CompletableFuture.completedFuture(Optional.ofNullable(users.get(userID)));
    }

    public CompletionStage<User> create(User user) {
        return get(user.getUserID())
            .thenApply(optionalUser -> optionalUser.orElseGet(() -> {
                users.put(user.getUserID(), user);
                return user;
            }));
    }

    public CompletionStage<Optional<User>> update(User user) {
        return get(user.getUserID())
            .thenApply(OptionalUser -> OptionalUser.map((ignore) -> {
                    users.put(user.getUserID(), user);
                    return user;
                })
            );
    }
}
