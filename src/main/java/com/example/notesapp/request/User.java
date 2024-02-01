package com.example.notesapp.request;

import lombok.Getter;

@Getter
public class User {
    private final String name;
    private final String userID;
    private final String password;

    public User(String name, String userID, String password) {
        this.name = name;
        this.userID = userID;
        this.password = password;
    }
}
