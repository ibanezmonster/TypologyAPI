package com.typology.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.user.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	
    public UserDetailsServiceImpl(AppUserRepository appUserRepository)
	{
		this.appUserRepository = appUserRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser user;
		
		try {
			user = appUserRepository.findByName(username)
	        				  	 	.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		}
		
		catch(UsernameNotFoundException e) {
			LOGGER.info("User Not Found with username: " + username);
			return null;
		}

    	return UserDetailsImpl.build(user);
    }
}