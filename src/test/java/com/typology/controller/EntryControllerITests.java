package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EntryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class EntryControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		
    
    @Autowired
    private EntryRepository entryRepository;
    
    @BeforeEach
   	void setup() {
    	//Typing class error on delete
    	//enneagramTypingConsensusRepository.deleteAll();
    	//entryRepository.deleteAll();    	
   	}
       
       
  @Test
  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void givenEntryObject_whenGetEntry_thenReturnSavedEntry() throws Exception{

  	//given
	EnneagramTypingConsensus enneagramTypingConsensus = EnneagramTypingConsensus.builder()
																			    .coreType(5)
																			    .wing(6)
																			    .tritypeOrdered(592)
																			    .tritypeUnordered(259)
																			    .overlay(613)
																			    .instinctMain("so")
																			    .instinctStack("so/sp")
																			    .instinctStackFlow("Contraflow")
																			    .exInstinctMain("CY")
																			    .exInstinctStack("CY/EX/SY")
																			    .exInstinctStackAbbreviation("i369")
																			    .exInstinctStackFlow("IPS")
																			    .build();
	  
	  Entry entry = new Entry();
	  entry.setName("Lain");
	  entry.setCategory(Category.FICTIONAL_CHARACTER);
	  entry.setEnneagramTypingConsensus(enneagramTypingConsensus);
	  
	  entryRepository.save(entry);
	 
      // when 
      ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}", entry.getName())	
							          .contentType(MediaType.APPLICATION_JSON));								

      
      // then
      response.andDo(print())										
              .andExpect(status().isOk())							
              .andExpect(jsonPath("$.name",							
                      is(entry.getName())));	      
  }
}
