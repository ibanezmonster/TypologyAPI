package com.typology.service.impl;

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
	
    public UserDetailsServiceImpl(AppUserRepository appUserRepository)
	{
		this.appUserRepository = appUserRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
    	AppUser user = appUserRepository.findByName(username)
    	        				  	 	.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    	return UserDetailsImpl.build(user);
    }
}