package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.integration.ContainerStartup;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class UserProfileControllerITests extends ContainerStartup
{
	
	@Autowired
    private MockMvc mockMvc;		

	@Autowired
	private AuthenticationManager authenticationManager;
		
 	@BeforeEach
   	void setup() {
        	
   	}
    
    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenAuthenticationPrincipal_whenViewUserProfile_thenOutputAuthenticationPrincipal() throws Exception{
    	
    	//given
    	
        
    	
    	//when
    	ResultActions response = mockMvc.perform(get("/api/v1/my_profile")
							            .contentType(MediaType.APPLICATION_JSON));
    										
    	    	
    	
    	//then
    	//expect viewing profile for: to not be null    	
    	response.andDo(print())							
        		.andExpect(status().isOk());
				//.andExpect(content().string("Viewing profile for: null"));	//works for body message! if writing invalid body response, test won't pass
    }
}
