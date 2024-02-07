package com.example.notesapp.controller;

import com.example.notesapp.request.User;
import com.example.notesapp.service.impl.TokenGenerationServiceImpl;
import com.example.notesapp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
