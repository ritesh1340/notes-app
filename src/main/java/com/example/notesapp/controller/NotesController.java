package com.example.notesapp.controller;

import com.example.notesapp.response.NotesResponse;
import com.example.notesapp.service.impl.NotesServiceImpl;
import com.example.notesapp.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RestController("/api/users/{userID}/notes")
public class NotesController {

    private final NotesServiceImpl notesService;
    private final UserServiceImpl userService;

    public NotesController(NotesServiceImpl notesService, UserServiceImpl userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping()
    public CompletionStage<NotesResponse> getAllNotes(@RequestHeader String token, @PathVariable String userID) {
        return userService.validateToken(userID, token)
            .thenCompose(__ -> notesService.getAll(userID));
    }
}
