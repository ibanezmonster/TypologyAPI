package com.typology.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.typology.exception.ExMessageBody;
import com.typology.exception.NotFoundException;
import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.security.AppUserRoles;
import com.typology.security.AppUserStatus;
import com.typology.service.AdminService;
import com.typology.utils.EnumStringComparison;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class AdminServiceImpl implements AdminService
{
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	
	
	public AdminServiceImpl(AppUserRepository appUserRepository)
	{
		this.appUserRepository = appUserRepository;
	}


	public ResponseEntity<String> editUserRole(String name, AppUser appUser)
	{	
		ResponseEntity<String> response = null;
		
		try {			
			//if user doesn't exist, throw error
			AppUser appUserDB = appUserRepository.findByName(name)
												 .orElseThrow(() -> {
											                            NotFoundException notFoundException = new NotFoundException("User with name \'" + name + "\' not found");
											                            return notFoundException;
											                        });
											                    	
			String foundName = appUserDB.getName();
			String oldRole = appUserDB.getRole();			
			String newRole = appUser.getRole();
						
			//handle bad input for role			
			if(newRole.isBlank() || newRole.isEmpty() || !EnumStringComparison.isStringInEnum(newRole.toUpperCase(), AppUserRoles.class)){
					throw new IllegalArgumentException("Invalid role selected.");
			}
			
			//update to new role			
			appUserDB.setRole(newRole.toUpperCase().strip());
			appUserRepository.save(appUserDB);
			
			response = ResponseEntity.status(HttpStatus.OK)
									 .body("For user: " + foundName + "," + " role of: \'" + oldRole.toUpperCase() + "\' is updated to: " + "\'" + newRole.toUpperCase() + "\'" + " role.");
		}
		
		catch(NotFoundException ex) {
            LOGGER.error("Error getting user {}", name, ex);
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
	                				 .body(ExMessageBody.MSG_PREFIX + ex.getMessage());
		}
		
		catch(IllegalArgumentException ex) {
            LOGGER.error("Invalid role selected when trying to update user role ", ex);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                				 .body(ExMessageBody.MSG_PREFIX + ex.getMessage());
		}
			
		catch(Exception ex) {
			LOGGER.error("Error occurred when trying to update user role ", ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                				 .body(ExMessageBody.MSG_PREFIX +  ex.getMessage());
		}
		
		return response;
	}
	
	
	public ResponseEntity<String> editUserStatus(String name, AppUser appUser)
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
			if(newStatusSetting.isBlank() || newStatusSetting.isEmpty() || !EnumStringComparison.isStringInEnum(newStatusSetting.toUpperCase(), AppUserStatus.class)){
				throw new IllegalArgumentException();
			}
			
			//update to new status
			appUserDB.setStatus(newStatusSetting.toLowerCase().strip());
			appUserRepository.save(appUserDB);
			
			response = ResponseEntity.status(HttpStatus.OK)
					 				 .body("For user: " + foundName + "," + " status of: \'" + oldStatusSetting + "\' is updated to: " + "\'" + newStatusSetting + "\'.");
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
