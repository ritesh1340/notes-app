package com.example.notesapp.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
@AllArgsConstructor
public class User {
    private final String name;
    private final String userID;
    private final String password;
}
