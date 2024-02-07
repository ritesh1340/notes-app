package com.example.notesapp.service.impl;

import com.example.notesapp.request.User;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerationServiceImpl {
    public String generateToken(String userID) {
        StringBuilder token = new StringBuilder(userID);
        token.reverse();
        return token.toString();
    }
}
