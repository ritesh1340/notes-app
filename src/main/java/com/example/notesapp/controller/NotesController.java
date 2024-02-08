package com.example.notesapp.controller;

import com.example.notesapp.request.Note;
import com.example.notesapp.response.NotesResponse;
import com.example.notesapp.service.impl.NotesServiceImpl;
import com.example.notesapp.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/users/{userID}/notes")
public class NotesController {

    private final NotesServiceImpl notesService;
    private final UserServiceImpl userService;

    public NotesController(NotesServiceImpl notesService, UserServiceImpl userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping("")
    public CompletionStage<NotesResponse> getAll(@RequestHeader String token, @PathVariable String userID) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.getAll(userID));
    }

    @PostMapping
    public CompletionStage<Note> create(@RequestHeader String token, @PathVariable String userID, @RequestBody String text) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.create(userID, text));
    }

    @DeleteMapping
    public CompletionStage<NotesResponse> deleteAll(@RequestHeader String token, @PathVariable String userID) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.deleteAll(userID));
    }

    @GetMapping("/{noteID}")
    public CompletionStage<Note> getByID(@RequestHeader String token, @PathVariable String userID, @PathVariable String noteID) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.getByID(userID, noteID));
    }

    @PatchMapping("/{noteID}")
    public CompletionStage<Note> updateByID(@RequestHeader String token, @PathVariable String userID, @PathVariable String noteID, @RequestBody String text) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.update(userID, noteID, text));
    }

    @DeleteMapping("/{noteID}")
    public CompletionStage<Void> deleteByID(@RequestHeader String token, @PathVariable String userID, @PathVariable String noteID) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.deleteByID(userID, noteID));
    }
}
