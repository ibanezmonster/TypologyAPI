package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class AuthoritiesRepositoryITests extends ContainerStartup
{
	@Autowired
	private AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;  
	
	private Authority authority;
	private AppUser user;
	
	
    @BeforeEach
    public void setup(){
    	user = new AppUser();
    	user.setName("testUser");
    	user.setPwd("FNUD*SFNU*EUFN*MWPF*EPW");
    	user.setRole("USER");
    	user.setStatus("enabled");
    	
    	authority = new Authority();
    	authority.setName("TEST_AUTHORITY");
    	authority.setUser(user);     	
    }
    
    
    
    @DisplayName("JUnit test for saving authority operation")
    @Test
    public void givenAuthorityObject_whenSave_thenReturnSavedAuthority(){

        //given
    	appUserRepository.save(user);
    	
        //when  	
    	Authority savedAuthority = authoritiesRepository.save(authority);

        // then
        assertThat(savedAuthority).isNotNull();
        assertThat(savedAuthority.getId()).isGreaterThan(0);
    }
    
    
    
}
