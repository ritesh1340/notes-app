package com.example.notesapp.response;

import com.example.notesapp.request.Note;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public class NotesResponse {
    private List<Note> notes;
}
