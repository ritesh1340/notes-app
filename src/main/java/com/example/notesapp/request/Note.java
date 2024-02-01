package com.example.notesapp.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Note {
    private String text;
    private String id;

    public Note() {

    }

    public Note(String text) {
        this.text = text;
        this.id = UUID.randomUUID().toString();
    }
}
