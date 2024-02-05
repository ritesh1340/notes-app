package com.example.notesapp.controller;

import com.example.notesapp.request.Note;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class NotesController {

    Map<String, List<Note>> notes = new HashMap<>();

    @GetMapping("/api/notes")
    public List<Note> getAllNotes(@RequestHeader String token) {
        if (!notes.containsKey(token)) {
            return null;
        }
        return notes.get(token);
    }

    @GetMapping("/api/notes/{noteID}")
    public Note getNoteByID(@RequestHeader String token, @PathVariable String noteID) {
        if (!notes.containsKey(token)) {
            return null;
        }
        return notes.get(token)
            .stream()
            .filter(noteForUser -> noteForUser.getId().equals(noteID))
            .toList()
            .get(0);
    }

    @PostMapping("/api/notes")
    public void createNote(@RequestHeader String token, @RequestBody Note note) {
        note.setId(UUID.randomUUID().toString());
        if (!notes.containsKey(token)) {
            notes.put(token, new ArrayList<>());
        }
        notes.get(token).add(note);
    }
}
