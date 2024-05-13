package com.typology.service.impl;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.typology.config.SecurityConfig;
import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.service.AppUserService;

import lombok.NoArgsConstructor;



@Service
public class AppUserServiceImpl implements AppUserService
{ 
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	
	private final Logger LOG = Logger.getLogger(AppUserServiceImpl.class.getName());

	
	public AppUserServiceImpl(AppUserRepository appUserRepository){
		this.appUserRepository = appUserRepository;
	}


	@Override
	public Optional<AppUser> getAppUserByName(String name)
	{
		Optional<AppUser> appUser = null;
		
		try {
			appUser = appUserRepository.findByName(name);
		}
		
		catch(ResourceNotFoundException e) {
			LOG.warning("Could not find username");
		}
		
		return appUser;
	}
	
	
	
	public AppUser findById(long id)
	{
		AppUser appUser = null;
		
		try {
			appUser = appUserRepository.findById(id)
			 				 		   .orElseThrow(ResourceNotFoundException::new);
		}
		
		catch(ResourceNotFoundException e) {
			LOG.warning("Could not find user");
		}
		
		return appUser;
	}
	
	
	public AppUser saveAppUser(AppUser appUser)
	{		
		try {
			appUser = appUserRepository.save(appUser);			 				 		   
		}
		
		catch(Exception e) {
			LOG.warning("Could not save app user");
		}
		
		return appUser;
	}
	
	
	
	
//	public AppUser findById(long id, AppUserRepository appUserRepository2)
//	{
//		AppUser appUser = null;
//		
//		try {
//			appUser = appUserRepository2.findById(id)
//			 				 		   .orElseThrow(ResourceNotFoundException::new);
//		}
//		
//		catch(ResourceNotFoundException e) {
//			LOG.warning("Could not find user");
//		}
//		
//		return appUser;
//	}
	
}
