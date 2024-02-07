package com.example.notesapp.service.impl;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerationServiceImpl {
    public String generateToken(String userID) {
        StringBuilder token = new StringBuilder(userID);
        token.reverse();
        return token.toString();
    }
}
