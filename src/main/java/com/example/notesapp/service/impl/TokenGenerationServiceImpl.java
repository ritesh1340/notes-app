package com.example.notesapp.service.impl;

import com.example.notesapp.request.User;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerationServiceImpl {
    public String generateToken(User user) {
        StringBuilder token = new StringBuilder(user.getUserID());
        token.reverse();
        return token.toString();
    }
}
