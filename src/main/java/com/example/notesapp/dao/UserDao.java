package com.example.notesapp.dao;

import com.example.notesapp.request.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class UserDao {

    public Map<String, User> users = new HashMap<>();

    public CompletionStage<Optional<User>> get(String userID) {
        return CompletableFuture.completedFuture(Optional.ofNullable(users.get(userID)));
    }

    public CompletionStage<User> create(User user) {
        users.put(user.getUserID(), user);
        return CompletableFuture.completedFuture(user);
    }

    public CompletionStage<User> update(User user) {
        return create(user);
    }
}
