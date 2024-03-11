package com.typology.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.security.AppUserRoles;
import com.typology.security.AppUserStatus;
import com.typology.service.AdminService;
import com.typology.utils.EnumStringComparison;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AppUserRepository appUserRepository;
	
	@PatchMapping("/update_user/{name}/role")
	public ResponseEntity<String> editUserRole(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		ResponseEntity<String> response = null;
		
		try {			
			//if user doesn't exist, throw error
			AppUser appUserDB = appUserRepository.findByName(name)
												 .orElseThrow(NoSuchElementException::new);
			String foundName = appUserDB.getName();
			String oldRole = appUserDB.getRole();			
			String newRole = appUser.getRole();
						
			//handle bad input for role
			if(!EnumStringComparison.isStringInEnum(newRole.toUpperCase(), AppUserRoles.class)){
					throw new IllegalArgumentException();
			}
			
			//update to new role			
			appUserDB.setRole(newRole.toUpperCase());
			appUserRepository.save(appUserDB);
			
			response = ResponseEntity.status(HttpStatus.OK)
									 .body("For user: " + foundName + "," + " role of: \'" + oldRole.toUpperCase() + "\' is updated to: " + "\'" + newRole.toUpperCase() + "\'" + " role.");
		}
		
		catch(NoSuchElementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
	                				 .body("User not found.");
		}
		
		catch(IllegalArgumentException ex) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                				 .body("Invalid role selected.");
		}
			
		catch(Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                				 .body("An exception occured due to " + ex.getMessage());
		}
		
		return response;
	}
	
	
	@PatchMapping("/update_user/{name}/status")
	public ResponseEntity<String> editUserStatus(@PathVariable String name, @RequestBody AppUser appUser)
	{	
		ResponseEntity<String> response = null;
		
		try {
			//if user doesn't exist, throw error
			AppUser appUserDB = appUserRepository.findByName(name)
												 .orElseThrow(NoSuchElementException::new);
			String foundName = appUserDB.getName();
			String oldStatusSetting = appUserDB.getStatus();
			
			
			
			String newStatusSetting = appUser.getStatus();
			
			
			//handle bad input for enabled
			if(!EnumStringComparison.isStringInEnum(newStatusSetting, AppUserStatus.class)){
				throw new IllegalArgumentException();
			}
			
			//update to new status
			appUserDB.setStatus(newStatusSetting);
			appUserRepository.save(appUserDB);
			
			response = ResponseEntity.status(HttpStatus.OK)
					 .body("For user: " + foundName + "," + " old status setting of: \'" + oldStatusSetting + "\' is updated to: " + "\'" + newStatusSetting);
		}
		
		
		catch(NoSuchElementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
	                				 .body("User not found.");
		}
			
		catch(Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                				 .body("An exception occured due to " + ex.getMessage());
		}
		
		return response;
	}
}
