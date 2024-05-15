package com.typology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.user.AppUser;

@RestController
@RequestMapping("/api/${api.version}/my_profile")
public class UserProfileController
{
	//works only in Dev, because security is enabled
	@GetMapping
	public ResponseEntity<?> viewMyProfile(@AuthenticationPrincipal String userName) {
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body("Viewing profile for: " + userName);		
	}
	
	
	//works only in testing, because security is disabled
//	@GetMapping
//	public ResponseEntity<?> viewMyProfile(@AuthenticationPrincipal User user) {
//		
//		return ResponseEntity.status(HttpStatus.OK)
//							 .body("Viewing profile for: " + user.getUsername());		
//	}
}
