package com.typology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.service.TypingService;

import jakarta.validation.Valid;


@RestController
//@PropertySource("classpath:Versioning.properties")
@RequestMapping("/api/${api.version}")
public class TypingController
{
	@Autowired
	private TypingService typingService;
	
	//view your own typing for an entry, using one typology system
	@GetMapping("/my_typings")		//if kept here, remove /profile
	public ResponseEntity<?> viewAllOfMyTypings() throws JsonProcessingException{		
		return typingService.viewAllOfMyTypings();
	}
	
	//view your own typing for an entry, using one typology system
	@GetMapping("/profile/{entryName}/my_typing/{typologySystem}")
	public ResponseEntity<?> viewTyping(@PathVariable String entryName, @PathVariable String typologySystem) throws JsonProcessingException{		
		return typingService.viewTyping(entryName, typologySystem);
	}
	
	
	//add your typing for an entry, using one typology system
	@PostMapping("/profile/{entryName}/vote/{typologySystem}")
	public ResponseEntity<String> addTyping(@PathVariable String entryName, @PathVariable String typologySystem, @Valid @RequestBody EnneagramTyping enneagramTyping){		
		return typingService.addTyping(entryName, typologySystem, enneagramTyping);
	}
	
	
	//update your typing for an entry, using one typology system
	@PatchMapping("/profile/{entryName}/vote/{typologySystem}")
	public ResponseEntity<String> updateTyping(@PathVariable String entryName, @PathVariable String typologySystem, @RequestBody EnneagramTyping enneagramTyping){		
		return typingService.updateTyping(entryName, typologySystem, enneagramTyping);
	}
	
	//delete your typing
	@DeleteMapping("/profile/{entryName}/my_typing/{typologySystem}")
	public ResponseEntity<HttpStatus> deleteTyping(@PathVariable String entryName, @PathVariable String typologySystem) {
		return typingService.deleteTyping(entryName, typologySystem);
	}
}