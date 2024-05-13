package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.RegistrationDTO;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;
import com.typology.repository.TypistRepository;
import com.typology.security.AppUserRoles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class RegistrationControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		

    @Autowired						
    private ObjectMapper objectMapper;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
   	private ModelMapper modelMapper;
      
    @BeforeEach
   	void setup() {
    	//typistRepository.deleteAll();    	
   	}
    
    
    
    @Test
    public void givenRegistrationDTO_whenRegisterAppUser_thenSaveUserAndTheirAuthorities() throws Exception{

    	//given
    	RegistrationDTO registrationDTO = RegistrationDTO.builder()
    													 .name("noob49")
    													 .pwd("Higurashi9549!")
    													 .build();
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/register")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(registrationDTO)));						
	
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isCreated());
    }
    
    
    
    
    
    
    
    @Test
    public void givenRegistrationDTO_whenRegisterAppUserWithBadPassword_thenReturn400Error() throws Exception{

    	//given
    	RegistrationDTO registrationDTO = RegistrationDTO.builder()
    													 .name("noob49")
    													 .pwd("!")
    													 .build();
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/register")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(registrationDTO)));						   	    	    			
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isBadRequest());
    }
    
    
    
    
    
    
    
    
    @Test
    public void givenRegistrationDTO_whenRegisterAppUserWithExistingName_thenReturnError() throws Exception{

    	//given
    	//save app user
    	AppUser appUser = new AppUser();
    	
    	RegistrationDTO registrationDTO = RegistrationDTO.builder()
    													 .name("noob49")
    													 .pwd("Higurashi9549!")
    													 .build();
    	
    	appUser = modelMapper.map(registrationDTO, AppUser.class);

    	String hashedPwd = passwordEncoder.encode(appUser.getPwd());
    	
    	appUser.setRole(AppUserRoles.USER.toString());
    	appUser.setStatus("enabled");
    	appUser.setPwd(hashedPwd);
    	
    	appUserRepository.save(appUser);
    	
		Authority authority = new Authority();        	
    	authority.setUser(appUser);
    	authority.setName("VIEWTYPINGS");    		
    	authoritiesRepository.save(authority);
    	

    	
    	// when
    	//save same app user that already exists
		ResultActions response = mockMvc.perform(post("/api/v1/register")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(registrationDTO)));						
			    	    	    			
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isConflict());
    }
}
