package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.service.impl.AppUserServiceImpl;



@ExtendWith(MockitoExtension.class)
public class AppUserServiceImplTests
{
	@Mock
    private AppUserRepository appUserRepository;

	@InjectMocks
    private AppUserServiceImpl appUserService;
	
    private AppUser appUser;
    
    @BeforeEach
    public void setup(){
    	appUserRepository = Mockito.mock(AppUserRepository.class);
    	appUserService = new AppUserServiceImpl(appUserRepository);
    	
    	appUser = AppUser.builder()
    					 .name("Ibanez")
    					 .pwd("haha")
    					 .registrationTimestamp(ZonedDateTime.now())
    					 .status("enabled")
    					 .build();
    }
    
    
    
    // JUnit test for saveAppUser method
    @DisplayName("JUnit service test for save app user")
    @Test
    public void givenAppUserObject_whenSave_thenReturnSavedAppUser(){
    	
        // given
        given(appUserRepository.save(appUser))
        					   .willReturn(appUser);
        
        // when                
        AppUser savedAppUser = appUserService.saveAppUser(appUser);
                
        // then
        assertThat(savedAppUser).isNotNull();
        assertThat(savedAppUser.getName()).isEqualTo("Ibanez");    	
    }
    
    
    
    
    
    
    // JUnit test for saveAppUser method
	@DisplayName("JUnit service test for find app user by id")
	@Test
	public void givenAppUserObject_whenSave_thenFindAppUserById(){
		
	    // given
	    given(appUserRepository.findById(appUser.getId()))
		   						.willReturn(Optional.of(appUser));

	    // when
	    AppUser foundAppUser = appUserService.findById(appUser.getId());
	    
	            
	    // then
	    assertThat(foundAppUser).isNotNull();
	    assertThat(foundAppUser.getName()).isEqualTo(appUser.getName());    	
	}
	
	
	
	
	
	// JUnit test for saveAppUser method
	@DisplayName("JUnit service test for find app user by name")
	@Test
	public void givenAppUserObject_whenSave_thenFindAppUserByName(){
		
	    // given
	    given(appUserRepository.findByName(appUser.getName()))
	    					   .willReturn(Optional.of(appUser));
	    	    
	    // when
	    AppUser foundAppUser = appUserService.getAppUserByName(appUser.getName());
	            
	    // then
	    assertThat(foundAppUser).isNotNull();
	    assertThat(foundAppUser.getName()).isEqualTo(appUser.getName());    	
	}
	
	
	
	
	// JUnit test for saveAppUser method
	@DisplayName("JUnit service test for find app user by name (negative scenario)")
	@Test
	public void givenEmptyAppUserObject_whenSave_thenFindAppUserByName(){
		
	    // given
	    given(appUserRepository.findByName(appUser.getName()))
	    					   .willReturn(Optional.empty());
	    	    
	    // when
	    AppUser foundAppUser = appUserService.getAppUserByName(appUser.getName());
	            
	    // then
	    assertThat(foundAppUser).isNull();  	
	}
}
