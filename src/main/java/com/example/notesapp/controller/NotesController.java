package com.example.notesapp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NotesController {

    List<String> notes = new ArrayList<>();

    @GetMapping("/notes")
    public List<String> getAllNotes(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return notes;
    }

    @PostMapping("/notes")
    public void createNote(@RequestBody String text) {
        notes.add(text);
    }
}
