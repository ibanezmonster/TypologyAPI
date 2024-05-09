package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.service.impl.UserDetailsServiceImpl;
import com.typology.user.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests
{
	@Mock
	private AppUserRepository appUserRepository;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	
	private AppUser appUser;
	
	
	@BeforeEach
	public void setup(){
		appUserRepository = Mockito.mock(AppUserRepository.class);
		userDetailsService = new UserDetailsServiceImpl(appUserRepository);
			
		appUser = new AppUser();    	
    	appUser.setId(123);
    	appUser.setName("Ibanez");
    	appUser.setPwd("haha");   
    	appUser.setRole("USER");
    	appUser.setRegistrationTimestamp(ZonedDateTime.now());
    	appUser.setStatus("enabled");    			
	}
	
	
	
	@DisplayName("JUnit service test for save entry")
	@Test
	public void givenEntryObject_whenSave_thenReturnSavedEntry(){
		
	    // given
	    given(appUserRepository.findByName(appUser.getName()))
	    					   .willReturn(Optional.of(appUser));
	    
	    // when                
	    UserDetails savedAppUserDetails = userDetailsService.loadUserByUsername(appUser.getName());
	            
	    // then
	    assertThat(savedAppUserDetails).isNotNull();
	    assertThat(savedAppUserDetails.getUsername()).isEqualTo(appUser.getName());    	
	}
	
	
	
	@DisplayName("JUnit service test for save entry (negative scenario)")
	@Test
	public void givenNullEntryObject_whenSave_thenReturnNothing(){
		
	    // given
	    given(appUserRepository.findByName(appUser.getName()))
	    					   .willReturn(Optional.empty());
	    
	    // when                
	    UserDetails savedAppUserDetails = userDetailsService.loadUserByUsername(appUser.getName());
	            
	    // then
	    assertThat(savedAppUserDetails).isNull();    	
	}
}
