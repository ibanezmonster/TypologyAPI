package com.typology.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.LoginDTO;
import com.typology.entity.user.AppUser;
import com.typology.integration.ContainerStartup;
import com.typology.repository.AppUserRepository;
import com.typology.security.AppUserRoles;
import com.typology.service.impl.AppUserServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc// do not disabled security //(addFilters = false)
@Testcontainers
public class LoginControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;	
	
    @Autowired						
    private ObjectMapper objectMapper;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	private ModelMapper modelMapper;
    
    @MockBean
    private SecurityContextHolder securityContextHolder;
    
    @Autowired
	private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private AppUserServiceImpl appUserService;
    
    @MockBean
    private Authentication authObj;
    
    @MockBean
    private AppUser appUser;
	
	
    
    
    
	@BeforeEach
   	void setup() {
    	//typistRepository.deleteAll();    	
   	}
    
    
	
    
    @Test
    public void givenAppUser_whenLogin_thenReturnSuccessfulLogin() throws Exception{
		
    	//given
    	//app user that is saved in database
    	String username = "noob49";
    	String pwd = "Higurashi9549!";
    	
    	String hashedPwd = passwordEncoder.encode(pwd);
    	
    	appUser = new AppUser();
    	appUser.setName(username);
    	appUser.setPwd(hashedPwd);
    	appUser.setRole(AppUserRoles.USER.toString());
    	appUser.setStatus("enabled");

    	appUserRepository.save(appUser);
    	
    	//login
    	LoginDTO loginDTO = LoginDTO.builder()
    								.name(username)
    								.pwd(pwd)
    								.build();

		appUser = modelMapper.map(loginDTO, AppUser.class);
    	
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/login")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(loginDTO)));	

	    //then
        response.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().string("Successful login"));
    }
    
    
    
    
    @Test
    public void givenAppUserWithLockedAccount_whenLogin_thenReturnError() throws Exception{
		
    	//given
    	//app user that is saved in database
    	String username = "noob49";
    	String pwd = "Higurashi9549!";
    	
    	String hashedPwd = passwordEncoder.encode(pwd);
    	
    	appUser = new AppUser();
    	appUser.setName(username);
    	appUser.setPwd(hashedPwd);
    	appUser.setRole(AppUserRoles.USER.toString());
    	appUser.setStatus("disabled");

    	appUserRepository.save(appUser);
    	
    	//login
    	LoginDTO loginDTO = LoginDTO.builder()
    								.name(username)
    								.pwd(pwd)
    								.build();

		appUser = modelMapper.map(loginDTO, AppUser.class);
    	
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/login")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(loginDTO)));	

	    //then
        response.andDo(print())
	            .andExpect(status().isUnauthorized())
	            .andExpect(content().string("Account is locked."));
    }
    
    
    
    
    
    
    
    @Test
    public void givenInvalidAppUser_whenLogin_thenReturnError() throws Exception{
		
    	//given
    	LoginDTO loginDTO = LoginDTO.builder()
    								.name("noob49")
    								.pwd("Higurashi1234!")
    								.build();
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/login")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(loginDTO)));	
	    //then
        response.andDo(print())
	            .andExpect(status().isNotFound());
    }
    
    
    
    
    
    
    @Test
    public void givenInvalidPassword_whenLogin_thenReturnError() throws Exception{
		
    	//given
    	//app user that is saved in database
    	String username = "noob49";
    	String pwd = "Higurashi9549!";
    	
    	String hashedPwd = passwordEncoder.encode(pwd);
    	
    	appUser = new AppUser();
    	appUser.setName(username);
    	appUser.setPwd(hashedPwd);
    	appUser.setRole(AppUserRoles.USER.toString());
    	appUser.setStatus("enabled");

    	appUserRepository.save(appUser);
    	
    	//matching username but wrong password
    	LoginDTO loginDTO = LoginDTO.builder()
    								.name("noob49")
    								.pwd("1Higurashi1234!") //wrong pwd
    								.build();
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/login")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(loginDTO)));	
	    //then
        response.andDo(print())
	            .andExpect(status().isUnauthorized());
    }
}
