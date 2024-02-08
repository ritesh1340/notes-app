package com.example.notesapp.service.impl;

import com.example.notesapp.dao.NotesDao;
import com.example.notesapp.request.Note;
import com.example.notesapp.response.NotesResponse;
import exception.NoteNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class NotesServiceImpl {
    private final NotesDao notesDao;

    public NotesServiceImpl(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    public CompletionStage<NotesResponse> getAll(String userID) {
        NotesResponse notesForUser = NotesResponse.builder().build();
        notesDao.getAll(userID)
            .thenApply(notesOptional -> notesOptional.map(notes -> notesForUser
                .toBuilder()
                .notes(notes)
                .build()));
        return CompletableFuture.completedFuture(notesForUser);
    }

    public CompletionStage<Note> create(String userID, String text) {
        return notesDao.create(userID, text);
    }

    public CompletionStage<NotesResponse> deleteAll(String userID) {
        return notesDao.deleteAll(userID)
            .thenCompose(__ -> getAll(userID));
    }

    public CompletionStage<Note> getByID(String userID, String noteID) {
        return notesDao.getByID(userID, noteID)
            .thenApply(noteOptional -> noteOptional
                .orElseThrow(() -> new NoteNotFoundException(userID, noteID)));
    }

    public CompletionStage<Void> deleteByID(String userID, String noteID) {
        return notesDao.deleteByID(userID, noteID);
    }

    public CompletionStage<Note> update(String userID, String noteID) {
        return getByID(userID, noteID)
            .thenCompose(note -> notesDao.updateByID(userID, note));
    }
}
