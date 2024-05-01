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
import org.springframework.http.ResponseEntity;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.service.impl.AdminServiceImpl;
import com.typology.service.impl.AppUserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTests
{
	@Mock
    private AppUserRepository appUserRepository;

	//@InjectMocks
    //private AppUserServiceImpl appUserService;
	
	@InjectMocks
    private AdminServiceImpl adminService;	
	
    private AppUser appUser;
    
    
    @BeforeEach
    public void setup(){
    	appUserRepository = Mockito.mock(AppUserRepository.class);
    	adminService = new AdminServiceImpl(appUserRepository);
    	
    	appUser = AppUser.builder()
    					 .name("Ibanez")
    					 .pwd("haha")
    					 .registrationTimestamp(ZonedDateTime.now())
    					 .status("enabled")
    					 .build();
    }
    
    
    
    @DisplayName("JUnit service test for edit user role")
    @Test
    public void givenAppUser_whenEditAppUserRole_thenReturnOkStatus(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        // when                
        ResponseEntity<String> editedAppUserRole = adminService.editUserRole(appUser.getName(), appUser);  
                        
        // then
        //assertThat(1).isEqualTo(1);
        assertThat(editedAppUserRole).isNotNull();            	
    }
    
    
    @DisplayName("JUnit service test for edit user status")
    @Test
    public void givenAppUser_whenEditAppUserStatus_thenReturnOkStatus(){
    	
        // given
    	given(appUserRepository.findByName(appUser.getName()))
		   					   .willReturn(Optional.of(appUser));
        
        // when                
        ResponseEntity<String> editedAppUserStatus = adminService.editUserStatus(appUser.getName(), appUser);  
                
        // then
        assertThat(editedAppUserStatus).isNotNull();            	
        //assertThat(savedAppUser.getName()).isEqualTo("Ibanez");    	
    }
}
