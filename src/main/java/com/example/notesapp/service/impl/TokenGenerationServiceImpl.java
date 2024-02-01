package com.example.notesapp.service.impl;

import com.example.notesapp.request.User;

public class TokenGenerationServiceImpl {
    public String generateToken(User user) {
        StringBuilder token = new StringBuilder(user.getUserID());
        token.reverse();
        return token.toString();
    }

    public TokenGenerationServiceImpl() {
    }
}
