package com.typology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.typology.repository.EntryRepository;

@SpringBootApplication
public class TypologyApiApplication {

	EntryRepository entryRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TypologyApiApplication.class, args);
	}

}
