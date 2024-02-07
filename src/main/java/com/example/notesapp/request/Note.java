package com.example.notesapp.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private String id;
    private String text;
}
