package com.example.notesapp.controller;

import com.example.notesapp.request.User;
import com.example.notesapp.service.impl.TokenGenerationServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    Map<String, User> users = new HashMap<>();
    TokenGenerationServiceImpl tokenGenerationService = new TokenGenerationServiceImpl();

    @PostMapping("/api/auth/signup")
    public String createUser(@RequestBody User user) {
        if (users.containsKey(user.getUserID())) {
            return "userID already exists, pick different ID";
        }
        users.put(user.getUserID(), user);
        return "new user created!";
    }

    @PostMapping("/api/auth/login")
    public String signIn(@RequestBody User user) {
        if (users.containsKey(user.getUserID()) && users.get(user.getUserID()).getPassword().equals(user.getPassword())) {
            return tokenGenerationService.generateToken(user);
        }
        return "Invalid Request";
    }
}
