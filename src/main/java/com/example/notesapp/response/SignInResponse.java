package com.example.notesapp.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResponse {
    private String token;
    private String tip;
}
