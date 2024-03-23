package com.typology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.service.TypingService;

import jakarta.validation.Valid;


@RestController
//@PropertySource("classpath:Versioning.properties")
@RequestMapping("/api/${api.version}/profile")
public class TypingController
{
	@Autowired
	private TypingService typingService;
	
	//view your own typing for an entry, using one typology system
	@GetMapping("/my_typings")		//if kept here, remove /profile
	public List<Typing> viewAllOfMyTypings(){		
		return typingService.viewAllOfMyTypings();
	}
	
	//view your own typing for an entry, using one typology system
	@GetMapping("/{entryName}/my_typing/{typologySystem}")
	public TypologySystemTyping viewTyping(@PathVariable String entryName, @PathVariable String typologySystem){		
		return typingService.viewTyping(entryName, typologySystem);
	}
	
	
	//add your typing for an entry, using one typology system
	@PostMapping("/{entryName}/vote/{typologySystem}")
	public ResponseEntity<String> addTyping(@PathVariable String entryName, @PathVariable String typologySystem, @Valid @RequestBody EnneagramTyping enneagramTyping){		
		return typingService.addTyping(entryName, typologySystem, enneagramTyping);
	}
	
	
	//add your typing for an entry, using one typology system
	@PatchMapping("/{entryName}/vote/{typologySystem}")
	public ResponseEntity<String> updateTyping(@PathVariable String entryName, @PathVariable String typologySystem, @RequestBody EnneagramTyping enneagramTyping){		
		return typingService.updateTyping(entryName, typologySystem, enneagramTyping);
	}
	
	@DeleteMapping(path="/{entryName}/my_typing/{typologySystem}")
	public ResponseEntity<String> deleteTyping(@PathVariable String entryName, @PathVariable String typologySystem) {
		return typingService.deleteTyping(entryName, typologySystem);
	}
}
