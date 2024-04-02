package com.typology.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.user.AppUser;
import com.typology.service.AdminService;


@RestController
@RequestMapping("/admin_console")		//401 unauthorized for non-admin
public class AdminController
{
	@Autowired
	private AdminService adminService;	
	
	@PatchMapping("/update_user/{name}/role")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> editUserRole(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		return adminService.editUserRole(name, appUser);
	}
	
	
	@PatchMapping("/update_user/{name}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> editUserStatus(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		return adminService.editUserStatus(name, appUser);
	}
}
