package com.typology.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.dto.AppUserRoleDTO;
import com.typology.dto.AppUserStatusDTO;
import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.service.AdminService;
import com.typology.service.EntryService;
import com.typology.service.impl.EntryServiceImpl;


@RestController
@RequestMapping("/console")		//cannot use "admin" in the URL, for some reason it becomes inaccessible when authenticating
public class AdminController
{
	@Autowired
	private AdminService adminService;	
	
	@Autowired
	private EntryServiceImpl entryService;
	
	@Autowired
	private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	
	private EnneagramTypingConsensus enneagramTypingConsensus;
	
//	@GetMapping
//	public ResponseEntity<?> testing(){
//		return ResponseEntity.ok().body("FDSFDSFDS");
//	}
//	
	@PostMapping("/add_entry")
	public ResponseEntity<String> addNewEntry(@RequestBody Entry entry){
		Entry entryResponse = null;
		
		try {			
			//add fk dependency for entry (blank enneagram type consensus)
			enneagramTypingConsensus = new EnneagramTypingConsensus();
			enneagramTypingConsensus.setCoreType(0);
			enneagramTypingConsensus.setWing(0);
			enneagramTypingConsensus.setInstinctMain("xx");
			enneagramTypingConsensus.setInstinctStack("xx/xx/xx");
			enneagramTypingConsensus.setInstinctStackFlow("xxxx");
			enneagramTypingConsensus.setOverlay(000);
			enneagramTypingConsensus.setTritypeOrdered(000);
			enneagramTypingConsensus.setTritypeUnordered(000);
			enneagramTypingConsensus.setExInstinctMain("xx");
			enneagramTypingConsensus.setExInstinctStack("xx");			
			enneagramTypingConsensus.setExInstinctStackFlow("xxx");
			
			//non-database fields
			enneagramTypingConsensus.setExInstinctStackAbbreviation(000);
			enneagramTypingConsensus.setExInstinctStackFlow("xx");
			
			enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
			entry.setEnneagramTypingConsensus(enneagramTypingConsensus);
			
			entryResponse = entryService.saveEntry(entry);
		}
		
		catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body("Error saving entry. Please enter valid name and category.");
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body("Error saving entry");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body("Entry is created for: " + entryResponse.getName());
	}
	
	@PatchMapping("/update_user/{name}/role")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> editUserRole(@PathVariable String name, @RequestBody AppUserRoleDTO appUserRoleDTO)
	{	
		return adminService.editUserRole(name, appUserRoleDTO);
	}
	
	
	@PatchMapping("/update_user/{name}/status")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> editUserStatus(@PathVariable String name, @RequestBody AppUserStatusDTO appUserStatusDTO)
	{	
		return adminService.editUserStatus(name, appUserStatusDTO);
	}
}
