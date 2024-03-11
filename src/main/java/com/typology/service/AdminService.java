package com.typology.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.typology.entity.user.AppUser;

public interface AdminService
{
	ResponseEntity<String> editUserRole(@PathVariable String name, @RequestBody AppUser appUser);
	ResponseEntity<String> editUserStatus(@PathVariable String name, @RequestBody AppUser appUser);
}
