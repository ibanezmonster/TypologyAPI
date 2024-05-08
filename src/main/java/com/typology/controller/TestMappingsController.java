package com.typology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.TestMappingsEntity;
import com.typology.entity.entry.Entry;
import com.typology.service.AdminService;
import com.typology.service.TestMappingsService;

@RestController
@RequestMapping("/test")		
public class TestMappingsController
{
	@Autowired
	private TestMappingsService testService;	

	
	@PostMapping("/makenew")
	public ResponseEntity<String> addNewEntry(@RequestBody TestMappingsEntity testBody){
		TestMappingsEntity testEntity = null;
		
		try {
			testEntity = testService.saveTestEntity(testBody);
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body("Error saving entry");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body("Entry is created for: " + testEntity.getName());
	}
}
