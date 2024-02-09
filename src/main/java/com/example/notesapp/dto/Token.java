package com.example.notesapp.dto;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String userID;
    private String userPassword;
    private String expiry;
}
