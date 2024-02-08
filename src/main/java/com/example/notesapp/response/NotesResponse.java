package com.example.notesapp.response;

import com.example.notesapp.request.Note;
import lombok.Builder;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class NotesResponse {
    @Builder.Default
    List<Note> notes = Collections.emptyList();
}
