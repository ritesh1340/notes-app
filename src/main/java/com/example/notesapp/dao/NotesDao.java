package com.example.notesapp.dao;

import com.example.notesapp.request.Note;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class NotesDao {

    Map<String, Map<String, Note>> notes = new HashMap<>();

    public CompletionStage<Optional<List<Note>>> getAll(String userID) {
        return CompletableFuture.completedFuture(
            Optional.ofNullable(notes.get(userID))
                    .map(notesForUser -> notesForUser.values().stream().toList()));
    }

    public CompletionStage<Note> create(String userID, String text) {
        Note note = Note.builder()
            .id(UUID.randomUUID().toString())
            .text(text)
            .build();
        notes.computeIfAbsent(userID, k -> new HashMap<>()).put(note.getId(), note);
        return CompletableFuture.completedFuture(note);
    }

    public CompletionStage<Void> deleteAll(String userID) {
        if (notes.containsKey(userID)) {
            notes.get(userID).clear();
        }
        return CompletableFuture.completedFuture(null);
    }

    public CompletionStage<Optional<Note>> getByID(String userID, String noteID) {
        return CompletableFuture.completedFuture(
            Optional.ofNullable(notes.get(userID))
                .map(notesForUser -> notesForUser.get(noteID))
        );
    }

    public CompletionStage<Note> updateByID(String userID, Note note) {
        notes.get(userID).put(note.getId(), note);
        return CompletableFuture.completedFuture(note);
    }

    public CompletionStage<Optional<List<Note>>> deleteByID(String userID, String noteID) {
        Optional.ofNullable(notes.get(userID))
            .map(notesOfUser -> notesOfUser.remove(userID));
        return CompletableFuture.completedFuture(null);
    }
}
