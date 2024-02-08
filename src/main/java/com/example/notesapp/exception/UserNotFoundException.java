package com.example.notesapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public UserNotFoundException(String userID) {
        this.errorCode = "USER_NOT_FOUND";
        this.message = String.format("User with userID %s not found", userID);
    }
}
