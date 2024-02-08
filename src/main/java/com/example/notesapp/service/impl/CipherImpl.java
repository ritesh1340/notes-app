package com.example.notesapp.service.impl;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CipherImpl {

    public String encodeBase64(String text){
        return new String(Base64.getEncoder().encode(text.getBytes()));
    }

    public byte[] decodeBase64(String encodedData){
        return Base64.getDecoder().decode(encodedData.getBytes());
    }
}
