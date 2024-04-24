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

import com.typology.entity.entry.Entry;
import com.typology.entity.user.AppUser;
import com.typology.service.AdminService;
import com.typology.service.EntryService;


@RestController
@RequestMapping("/console")		//cannot use "admin" in the URL, for some reason it becomes inaccessible when authenticating
public class AdminController
{
	@Autowired
	private AdminService adminService;	
	
	@Autowired
	private EntryService entryService;
	
	@GetMapping
	public ResponseEntity<?> testing(){
		return ResponseEntity.ok().body("FDSFDSFDS");
	}
	
	@PostMapping("/add_entry")
	public ResponseEntity<String> addNewEntry(@RequestBody Entry entry){
		Entry entryResponse = null;
		
		try {
			entryResponse = entryService.saveEntry(entry);
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
	public ResponseEntity<String> editUserRole(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		return adminService.editUserRole(name, appUser);
	}
	
	
	@PatchMapping("/update_user/{name}/status")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> editUserStatus(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		return adminService.editUserStatus(name, appUser);
	}
}
