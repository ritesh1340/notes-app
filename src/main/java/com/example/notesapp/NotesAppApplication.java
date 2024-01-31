package com.example.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NotesAppApplication {

	public static void main(String[] args) {
		System.out.println("Ritesh Says hello");
		SpringApplication.run(NotesAppApplication.class, args);
	}

}
