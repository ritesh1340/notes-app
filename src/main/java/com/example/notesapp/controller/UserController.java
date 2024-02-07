package com.example.notesapp.controller;

import com.example.notesapp.request.User;
import com.example.notesapp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/api/auth/signup")
    public CompletionStage<User> create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/api/auth/login")
    public CompletionStage<String> signIn(@RequestBody User user) {
        return userService.getToken(user);
    }

    @PatchMapping("/api/users")
    public CompletionStage<User> update(@RequestBody User user, @RequestHeader String token) {
        return userService.update(user, token);
    }
}
