package com.typology.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.info.Teacher;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.service.EntryService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/${api.version}/profile")
public class EntryController
{
	@Autowired
	private EntryService entryService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//@GetMapping("/api/entry")
//	@GetMapping("/all")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Entry> getAllEntries(){
//		return entryService.getAllEntries(); 
//	}
	
	
	//view profile's consensus typings
	@GetMapping("/{entryName}")	
	public ResponseEntity<?> getEntry(@PathVariable String entryName) throws JsonProcessingException{
		Entry entry = null;
		
		try {
			entry = entryService.getEntry(entryName)
					  			.orElseThrow(ResourceNotFoundException::new);;			
		}
		
		catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
							     .body("Entry not found");
		}
		
		catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					 			 .body("Entry not found");
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body("Internal server error");
		}
		
		
		return ResponseEntity.status(HttpStatus.OK)
			     			 .body(objectMapper.writeValueAsString(entry));		
	}
	
	
	
	
	
	
	
	
	//delete a profile
//	@DeleteMapping("/{entry}/delete")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public HttpStatus deleteEntry(@PathVariable String entry){
//		entryService.deleteEntry(entry);
//		return HttpStatus.NO_CONTENT;
//	}
	
	
	
//	@PutMapping("/search_by_system/enneagram/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	public Entry updateEntry(@PathVariable long id, @RequestBody Entry entry){
//		return entryService.updateEntry(entry);
//	}
	
	
	
	////////tests///////////////////////
	
	//example for responseentity
	//	@GetMapping("/api/entry")
	//	public ResponseEntity<List<Entry>> getAllEntries(){
	//		return ResponseEntity.ok().body(entryService.getAllEntries()); 
	//	}
	
	
//	@GetMapping("/enneagram/{type}")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Entry> findAllOfEnneagramCoreType(@PathVariable int type){
//		return entryService.findAllOfEnneagramCoreType(type);
//	}
	
	
		
	////////tests///////////////////////
}