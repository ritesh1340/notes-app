package com.example.notesapp.service.impl;

import com.example.notesapp.dto.Token;
import com.example.notesapp.response.SignInResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class TokenGenerationServiceImpl {

    private static final long TOKEN_EXPIRY_DURATION = 300;
    private final CipherImpl cipher;

    public TokenGenerationServiceImpl(CipherImpl cipher) {
        this.cipher = cipher;
    }

    public CompletionStage<SignInResponse> generateToken(String userID, String userPassword) throws JsonProcessingException {
        Instant expiry = getExpiry();
        String tip = String.format("Use the token above to handle notes. Token shall expire at %s", expiry.toString());
        return CompletableFuture.completedFuture(SignInResponse.builder()
            .token(buildEncryptedToken(userID, userPassword, expiry))
            .tip(tip)
            .build());
    }

    public CompletionStage<Token> getDecryptedToken(String token) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String decodedJsonString = new String(cipher.decodeBase64(token), StandardCharsets.UTF_8);
        return CompletableFuture.completedFuture(objectMapper.readValue(decodedJsonString, Token.class));
    }

    private String buildEncryptedToken(String userID, String userPassword, Instant expiry) throws JsonProcessingException {
        Token token = Token.builder()
            .userID(userID)
            .userPassword(userPassword)
            .expiry(expiry.toString())
            .build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return cipher.encodeBase64(ow.writeValueAsString(token));
    }

    private Instant getExpiry() {
        return Instant.now().plusSeconds(TOKEN_EXPIRY_DURATION);
    }
}
