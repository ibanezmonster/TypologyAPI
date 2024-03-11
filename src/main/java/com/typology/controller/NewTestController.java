package com.typology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.typology.repository.EntryRepository;

@RestController
public class NewTestController
{
	@Autowired
	private EntryRepository entryRepository;
	
	@PostMapping("/testing")
	ResponseEntity<String> testingStuff(@RequestBody String param){
		ResponseEntity<String> response = null;
		
		response = ResponseEntity.status(HttpStatus.OK)
								 .body("OK");
		
		return response;
	}
}
