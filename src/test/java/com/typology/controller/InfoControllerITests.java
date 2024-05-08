package com.typology.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;


import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
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
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EntryRepository;
import com.typology.repository.TypistRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class InfoControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		

    @Autowired						
    private ObjectMapper objectMapper;
    
    @Autowired
    private TypistRepository typistRepository;
      
    @BeforeEach
   	void setup() {
    	//typistRepository.deleteAll();    	
   	}
    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenEntryObject_whenGetEntry_thenReturnSavedEntry() throws Exception{

	  //given
      List<Typist> typists = new ArrayList<>();
      
      Typist t1 = new Typist();
      t1.setName("typist1");
      
      Typist t2 = new Typist();
      t2.setName("typist2");
      
      typists.add(t1);
      typists.add(t2);
      
      typistRepository.saveAll(typists);
      
      
	  
		// when
	  	//List<Typist> typists = typistRepository.findAll();
      
      //need to specify v1
      //java.lang.IllegalArgumentException: Not enough variable values available to expand 'api.version'

		
		ResultActions response = mockMvc.perform(get("http://localhost:8080/api/v1/info/typists")	
							            .contentType(MediaType.APPLICATION_JSON)											
							            .content(objectMapper.writeValueAsString(typists)));						
	
	    
	    // then
	    response.andDo(print())										
	            .andExpect(status().isOk())		            
	            .andExpect(jsonPath("$.size()", 
	            		is(greaterThanOrEqualTo(typists.size()))));		//greater than since entries may exist previously if not deleted beforehand
    }
}
