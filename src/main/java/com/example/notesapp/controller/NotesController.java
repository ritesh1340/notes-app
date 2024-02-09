package com.example.notesapp.controller;

import com.example.notesapp.request.Note;
import com.example.notesapp.response.NotesResponse;
import com.example.notesapp.service.impl.NotesServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/users/{userID}/notes")
public class NotesController {

    private final NotesServiceImpl notesService;

    public NotesController(NotesServiceImpl notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public CompletionStage<NotesResponse> getAll(@PathVariable String userID) {
        return notesService.getAll(userID);
    }

    @PostMapping
    public CompletionStage<Note> create(@PathVariable String userID, @RequestBody String text) {
        return notesService.create(userID, text);
    }

    @DeleteMapping
    public CompletionStage<NotesResponse> deleteAll(@PathVariable String userID) {
        return notesService.deleteAll(userID);
    }

    @GetMapping("/{noteID}")
    public CompletionStage<Note> getByID(@PathVariable String userID, @PathVariable String noteID) {
        return notesService.getByID(userID, noteID);
    }

    @PatchMapping("/{noteID}")
    public CompletionStage<Note> updateByID(@PathVariable String userID, @PathVariable String noteID, @RequestBody String text) {
        return notesService.update(userID, noteID, text);
    }

    @DeleteMapping("/{noteID}")
    public CompletionStage<Void> deleteByID(@PathVariable String userID, @PathVariable String noteID) {
        return notesService.deleteByID(userID, noteID);
    }
}
