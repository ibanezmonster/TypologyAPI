package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.LoginDTO;
import com.typology.dto.RegistrationDTO;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.integration.ContainerStartup;
import com.typology.security.AppUserRoles;
import com.typology.service.AppUserService;
import com.typology.user.UserDetailsImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class LoginControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;	
	
    @Autowired						
    private ObjectMapper objectMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	private ModelMapper modelMapper;
    
    @MockBean
	private AuthenticationManager authenticationManager;
    
    @MockBean
    private AppUserService appUserService;
    
    @MockBean
    private Authentication authObj;
    
    @MockBean
    private AppUser appUser;
	
	
	@BeforeEach
   	void setup() {
    	//typistRepository.deleteAll();    	
   	}
    
    
    
    @Test
    @WithMockUser(username = "noob49", password = "Higurashi9549!", roles = {"USER"})
    public void givenAppUser_whenLogin_thenReturnSuccessfulLogin() throws Exception{
		
    	//given
    	LoginDTO loginDTO = LoginDTO.builder()
    								.name("noob49")
    								.pwd("Higurashi9549!")
    								.build();
    	
    	String hashedPwd = passwordEncoder.encode(loginDTO.getPwd());

		appUser = modelMapper.map(loginDTO, AppUser.class);


    	appUser.setRole(AppUserRoles.USER.toString());
    	appUser.setStatus("enabled");
    	appUser.setPwd(hashedPwd);

    	
        given(appUserService.getAppUserByName(appUser.getName())).willReturn(appUser);
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/login")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(loginDTO)));	

    	
    	
	    //then
        response.andDo(print())
	            .andExpect(status().isOk());
    }
}
