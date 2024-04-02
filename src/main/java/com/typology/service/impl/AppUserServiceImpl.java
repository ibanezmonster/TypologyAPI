package com.typology.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.typology.config.SecurityConfig;
import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.service.AppUserService;



@Service
public class AppUserServiceImpl implements AppUserService
{ 
	
	@Autowired
	AppUserRepository appUserRepository;
	
	
	private final Logger LOG = Logger.getLogger(AppUserServiceImpl.class.getName());

	
	@Override
	public AppUser getAppUserByName(String name)
	{
		AppUser appUser = null;
		
		try {
			appUser = appUserRepository.findByName(name)
			 				 		   .orElseThrow(ResourceNotFoundException::new);
		}
		
		catch(ResourceNotFoundException e) {
			LOG.warning("Could not find username");
		}
		
		return appUser;
	}
}
