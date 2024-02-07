package com.example.notesapp.controller;

import com.example.notesapp.request.User;
import com.example.notesapp.service.impl.TokenGenerationServiceImpl;
import com.example.notesapp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    TokenGenerationServiceImpl tokenGenerationService;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/api/auth/signup")
    public User create(@RequestBody User user) {
        return userService.create(user).toCompletableFuture().join();
    }

    @PostMapping("/api/auth/login")
    public String signIn(@RequestBody User user) {
        return userService.get(user.getUserID())
            .thenApply(existingUser -> tokenGenerationService.generateToken(existingUser))
            .toCompletableFuture()
            .join();
    }
}
