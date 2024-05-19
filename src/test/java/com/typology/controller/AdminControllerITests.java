package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.AppUserRoleDTO;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.repository.AppUserRepository;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EntryRepository;
import com.typology.security.AppUserRoles;
import com.typology.service.AppUserService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class AdminControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired						
    private ObjectMapper objectMapper;
    
    @BeforeEach
   	void setup() {
    	//Typing class error on delete
    	//enneagramTypingConsensusRepository.deleteAll();
    	//entryRepository.deleteAll();    	
   	}
       
       
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenNewEnty_whenAddNewEntry_thenReturnSavedEntry() throws Exception{

	  //given		
	  Entry entry = new Entry();
	  entry.setName("Lain");
	  entry.setCategory(Category.ANIME);
	 
      // when 
      ResultActions response = mockMvc.perform(post("/console/add_entry")	
							          .contentType(MediaType.APPLICATION_JSON)
    		  						  .content(objectMapper.writeValueAsString(entry)));
      // then
      response.andDo(print())										
              .andExpect(status().isCreated());	      
  }
  
  
  
  
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenNewEnty_whenAddNewEntryButOmitCategory_thenThrowError() throws Exception{

	  //given
	  Entry entry = new Entry();
	  entry.setName("Lain");

	 
      // when 
      ResultActions response = mockMvc.perform(post("/console/add_entry")	
							          .contentType(MediaType.APPLICATION_JSON)
    		  						  .content(objectMapper.writeValueAsString(entry)));								

      
      // then
      response.andDo(print())										
              .andExpect(status().isBadRequest());	      
  }
  
  
  
  
  
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenNewEnty_whenAddNewCategoryButOmitName_thenThrowError() throws Exception{

	  //given
	  Entry entry = new Entry();
	  entry.setCategory(Category.ANIME);

	 
      // when 
      ResultActions response = mockMvc.perform(post("/console/add_entry")	
							          .contentType(MediaType.APPLICATION_JSON)
    		  						  .content(objectMapper.writeValueAsString(entry)));								

      
      // then
      response.andDo(print())										
              .andExpect(status().isBadRequest());	      
  }
  
  
  
  
  
  
  
  
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenAppUser_whenEditRole_thenReturnAppUserUpdatedRole() throws Exception{

	  //given
	  AppUser appUser = new AppUser();
  	  appUser.setName("Kyon");
  	  appUser.setRole(AppUserRoles.USER.toString());
  	  appUser.setStatus("enabled");
  	  appUser.setPwd("haha");
  	  appUser.setRegistrationTimestamp(ZonedDateTime.now());

  	
	  appUserRepository.save(appUser);
	  
	  AppUserRoleDTO appuserRoleDTO = new AppUserRoleDTO();
	  appuserRoleDTO.setRole(AppUserRoles.CONTRIBUTOR.toString());
	  
	  //when 
      ResultActions response = mockMvc.perform(patch("/console/update_user/{name}/role", appUser.getName())	
							          .contentType(MediaType.APPLICATION_JSON)
							          .characterEncoding("UTF-8")
    		  						  .content(objectMapper.writeValueAsString(appuserRoleDTO)));								
    
      // then
      response.andDo(print())										
              .andExpect(status().isOk())
              .andExpect(content().string("For user: Kyon, role of: 'USER' is updated to: 'CONTRIBUTOR' role."));
  }
  
  
  
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenAppUser_whenEditStatus_thenReturnAppUserUpdatedStatus() throws Exception{

	  //given
	  AppUser appUser = new AppUser();
	  appUser.setName("Kyon");
	  appUser.setRole(AppUserRoles.USER.toString());
	  appUser.setStatus("enabled");
	  appUser.setPwd("haha");
  	  appUser.setRegistrationTimestamp(ZonedDateTime.now());


	  appUserRepository.save(appUser);
	  
	  
	  appUser.setStatus("disabled");

	  
	  
	  //when 
      ResultActions response = mockMvc.perform(patch("/console/update_user/{name}/status", appUser.getName())	
							          .contentType(MediaType.APPLICATION_JSON)
							          .characterEncoding("UTF-8")
    		  						  .content(objectMapper.writeValueAsString(appUser)));								
	  
      // then
      response.andDo(print())										
              .andExpect(status().isOk());
  }
}
