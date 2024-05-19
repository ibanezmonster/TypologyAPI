package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.typology.dto.AppUserRoleDTO;
import com.typology.dto.AppUserStatusDTO;
import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.security.AppUserRoles;
import com.typology.security.AppUserStatus;
import com.typology.service.impl.AdminServiceImpl;
import com.typology.service.impl.AppUserServiceImpl;
import com.typology.utils.EnumStringComparison;

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
    					 .role(AppUserRoles.USER.toString())
    					 .status(AppUserStatus.ENABLED.toString())
    					 .build();
    }
    
    
    
    @DisplayName("JUnit service test for edit user role")
    @Test
    public void givenAppUser_whenEditAppUserRole_thenReturnUpdatedRole(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        AppUserRoleDTO appUserDTO = new AppUserRoleDTO();
        appUserDTO.setRole(AppUserRoles.CONTRIBUTOR.toString());
        
        // when                
        ResponseEntity<String> editedAppUserRole = adminService.editUserRole(appUser.getName(), appUserDTO);  
                        
        // then
        assertThat(editedAppUserRole).isNotNull();  
        assertThat(editedAppUserRole.getStatusCode().equals(HttpStatus.OK));
        assertThat(editedAppUserRole).asString().containsIgnoringWhitespaces(AppUserRoles.CONTRIBUTOR.toString());
    }
    
    
    @DisplayName("JUnit service test for edit user status")
    @Test
    public void givenAppUser_whenEditAppUserStatus_thenReturnUpdatedStatus(){
    	
        // given
    	given(appUserRepository.findByName(appUser.getName()))
		   					   .willReturn(Optional.of(appUser));
        
    	AppUserStatusDTO appUserDTO = new AppUserStatusDTO();
        appUserDTO.setStatus(AppUserStatus.DISABLED.toString());
        
        // when                
        ResponseEntity<String> editedAppUserStatus = adminService.editUserStatus(appUser.getName(), appUserDTO);  
                
        // then
        assertThat(editedAppUserStatus).isNotNull();   
        assertThat(editedAppUserStatus.getStatusCode().equals(HttpStatus.OK));
        assertThat(editedAppUserStatus).asString().containsIgnoringWhitespaces(AppUserStatus.DISABLED.toString());
    }
    
    
    
    
    @DisplayName("JUnit service test for edit user role with invalid value")
    @Test
    public void givenAppUser_whenEditAppUserRole_thenReturnError(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        
        AppUser fakeAppUser  = AppUser.builder()
				 .name("fakey")
				 .pwd("fdsa")
				 .registrationTimestamp(ZonedDateTime.now())
				 .role(AppUserRoles.USER.toString())
				 .status(AppUserStatus.ENABLED.toString())
				 .build();
        
        
        String invalidValue = "some dumb invalid value";
        
        AppUserRoleDTO appUserDTO = new AppUserRoleDTO();
        appUserDTO.setRole(invalidValue);
        
        // when                
        ResponseEntity<String> editedAppUserRole = adminService.editUserRole(fakeAppUser.getName(), appUserDTO);  
                        
        // then
        assertThat(editedAppUserRole).asString().doesNotContain(invalidValue);
        assertThat(editedAppUserRole.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
    
    
    
    @DisplayName("JUnit service test for edit user status with invalid value")
    @Test
    public void givenAppUser_whenEditAppUserStatus_thenReturnError(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        AppUser fakeAppUser  = AppUser.builder()
				 .name("fakey")
				 .pwd("fdsa")
				 .registrationTimestamp(ZonedDateTime.now())
				 .role(AppUserRoles.USER.toString())
				 .status(AppUserStatus.ENABLED.toString())
				 .build();
       
       
       String invalidValue = "some dumb invalid value";
       
       AppUserStatusDTO appUserDTO = new AppUserStatusDTO();
       appUserDTO.setStatus(invalidValue);
        

		// when                
        ResponseEntity<String> editedAppUserStatus = adminService.editUserStatus(fakeAppUser.getName(), appUserDTO);  
                        
        // then
        assertThat(editedAppUserStatus).asString().doesNotContain(invalidValue);
        assertThat(editedAppUserStatus.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
    
    
    
    @DisplayName("JUnit service test for edit nonexistent user with valid role")
    @Test
    public void givenNonexistentAppUser_whenEditAppUserRole_thenReturnError(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        AppUser fakeAppUser  = AppUser.builder()
									 .name("fakey")
									 .pwd("fdsa")
									 .registrationTimestamp(ZonedDateTime.now())
									 .role(AppUserRoles.USER.toString())
									 .status(AppUserStatus.ENABLED.toString())
									 .build();
        
        AppUserRoleDTO appUserDTO = new AppUserRoleDTO();
        appUserDTO.setRole(AppUserRoles.CONTRIBUTOR.toString());
        
        // when                
        ResponseEntity<String> editedAppUserRole = adminService.editUserRole(fakeAppUser.getName(), appUserDTO);  
                        
        // then
        assertThat(editedAppUserRole.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }
    
    
    
    
    @DisplayName("JUnit service test for edit nonexistent user with valid status")
    @Test
    public void givenNonexistentAppUser_whenEditAppUserStatus_thenReturnError(){
    	
        // given
        given(appUserRepository.findByName(appUser.getName()))
        					   .willReturn(Optional.of(appUser));
        
        AppUser fakeAppUser  = AppUser.builder()
									 .name("fakey")
									 .pwd("fdsa")
									 .registrationTimestamp(ZonedDateTime.now())
									 .role(AppUserRoles.USER.toString())
									 .status(AppUserStatus.ENABLED.toString())
									 .build();

        AppUserStatusDTO appUserDTO = new AppUserStatusDTO();        
        appUserDTO.setStatus(AppUserStatus.DISABLED.toString());
        
        // when                
        ResponseEntity<String> editedAppUserStatus = adminService.editUserStatus(fakeAppUser.getName(), appUserDTO);  
                        
        // then
        assertThat(editedAppUserStatus.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }
}
