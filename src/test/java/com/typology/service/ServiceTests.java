package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.typology.TypologyApiApplication;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.repository.AppUserRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.service.impl.AppUserServiceImpl;


import org.springframework.test.context.junit4.SpringRunner;


//@DataJpaTest
@SpringBootTest//(classes = TypologyApiApplication.class)
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)  // n/a
//@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)			//doesn't do anything
public class ServiceTests
{
	@Mock
    private AppUserRepository appUserRepository;

	@InjectMocks
    private AppUserServiceImpl appUserService;// = new AppUserServiceImpl();
	
    private AppUser appUser;
    
    
//    @Autowired
//    private EnneagramTypingRepository enneagramTypingRepository;
//    
//    @Autowired
//    private TypistRepository typistRepository;
//    
//    
//    private Set<Authority> authorities;
//    private Authority viewTypings;
    
    
    
    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(ServiceTests.class);
    }
    
    
    
    @BeforeEach
    public void setup(){
    	appUserRepository = Mockito.mock(AppUserRepository.class);
    	appUserService = new AppUserServiceImpl(appUserRepository);
    	
    	
//    	appUser = new AppUser();    	
//    	appUser.setId(123L);
//    	appUser.setName("Ibanez");
//    	appUser.setPwd("haha");    	
//    	appUser.setRegistrationTimestamp(ZonedDateTime.now());
//    	appUser.setStatus("enabled");
    	
    	appUser = AppUser.builder()
    					 .id(123)
    					 .name("Ibanez")
    					 .pwd("haha")
    					 .registrationTimestamp(ZonedDateTime.now())
    					 .status("enabled")
    					 .build();
    }
    
    
    
    
    
    
    // JUnit test for saveAppUser method
    @DisplayName("JUnit test for saveAppUser method")
    @Test
    public void givenAppUserObject_whenSave_thenFindAppUserById(){
    	
        // given
        given(appUserRepository.save(appUser))
        					   .willReturn(appUser);

        // when                
        AppUser savedAppUser = appUserService.saveAppUser(appUser);
                
        // then
        assertThat(savedAppUser).isNotNull();
        assertThat(savedAppUser.getName()).isEqualTo("Ibanez");    	
    }
}
