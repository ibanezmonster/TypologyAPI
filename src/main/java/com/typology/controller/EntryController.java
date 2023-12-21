package com.typology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.typology.entity.entry.Entry;
import com.typology.entity.info.Teacher;
import com.typology.service.EntryService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RequestMapping("/api/entry")
@RestController
@AllArgsConstructor
public class EntryController
{
	@Autowired
	private EntryService entryService;
	
	//@GetMapping("/api/entry")
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public List<Entry> getAllEntries(){
		return entryService.getAllEntries(); 
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Entry getEntry(@PathVariable long id){
		return entryService.getEntry(id); 
	}
		
	@PostMapping//("/entry")
    @ResponseStatus(HttpStatus.CREATED)
	public Entry createEntry(@RequestBody Entry entry){
		return entryService.createEntry(entry);
	}	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Entry updateEntry(@PathVariable long id, @RequestBody Entry entry){
		return entryService.updateEntry(entry);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public HttpStatus deleteEntry(@PathVariable long id){
		entryService.deleteEntry(id);
		return HttpStatus.NO_CONTENT;
	}
	
	
	////////tests///////////////////////
	
	//example for responseentity
	//	@GetMapping("/api/entry")
	//	public ResponseEntity<List<Entry>> getAllEntries(){
	//		return ResponseEntity.ok().body(entryService.getAllEntries()); 
	//	}
	
	
	@GetMapping("/entry/enneagram/{type}")
	@ResponseStatus(HttpStatus.OK)
	public List<Entry> findAllOfEnneagramCoreType(@PathVariable int type){
		return entryService.findAllOfEnneagramCoreType(type);
	}
	
	
	@GetMapping("/entry/enneagram/2")
	@ResponseStatus(HttpStatus.OK)
	public List<Entry> findAllOfEnneagramCoreType2(){
		return entryService.findAllOfEnneagramCoreType2();
	}
	
	//@GetMapping("/api/entry/enneagram/9")
		//public ResponseEntity<List<Entry>> findAllOfEnneagramCoreType(){
		//return ResponseEntity.ok().body(entryService.findAllOfEnneagramCoreType(type));	
		
	////////tests///////////////////////
}