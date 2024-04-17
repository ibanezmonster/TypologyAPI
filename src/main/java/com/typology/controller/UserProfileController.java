package com.typology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.user.AppUser;

@RestController
@RequestMapping("/api/${api.version}/my_profile")
public class UserProfileController
{
	@GetMapping
	public ResponseEntity<?> viewMyProfile(@AuthenticationPrincipal String userName) {
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body("Viewing profile for: " + userName);		
	}
}
