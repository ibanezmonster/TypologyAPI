package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.typology.entity.user.AppUser;
import com.typology.integration.ContainerStartup;
import com.typology.security.AppUserRoles;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class AppUserRepositoryITests extends ContainerStartup
{
	@Autowired
	private AppUserRepository appUserRepository;   
	private AppUser appUser;
	 
	
    @BeforeEach
    public void setup(){
    	
      appUser = new AppUser();
  	  appUser.setName("Kyon");
  	  appUser.setRole(AppUserRoles.USER.toString());
  	  appUser.setStatus("enabled");
  	  appUser.setPwd("haha");
  	  appUser.setRegistrationTimestamp(ZonedDateTime.now());
    }
    
    
    
     
    @DisplayName("JUnit test for creating app user operation")
    @Test
    public void givenAppUserObject_whenSave_thenReturnSavedAppUser(){

        //given
    	
        //when  	
    	AppUser savedAppUser = appUserRepository.save(appUser);

        // then
        assertThat(savedAppUser).isNotNull();
        assertThat(savedAppUser.getId()).isGreaterThan(0);
    }
    
    
    
    

    @DisplayName("JUnit test for creating app user operation and find by name")
    @Test
    public void givenAppUserObject_whenSave_thenFindAppUserByName(){

        //given
    	appUserRepository.save(appUser);
    		
        // when  	
    	Optional<AppUser> foundAppUser = appUserRepository.findByName(appUser.getName());

        // then
        assertThat(foundAppUser).get().isNotNull();
        assertThat(foundAppUser.get().getId()).isEqualTo(appUser.getId());
    }
}
