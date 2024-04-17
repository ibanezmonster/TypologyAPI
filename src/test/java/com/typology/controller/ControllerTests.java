package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.service.AppUserService;
import com.typology.service.EnneagramTypingConsensusService;
import com.typology.service.EntryService;
import com.typology.service.impl.TypingServiceImpl;
import com.typology.service.impl.TypistServiceImpl;

import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.BDDMockito.willDoNothing;

//There can be multiple root causes for this exception. For me, my mockMvc wasn't getting auto-configured. 
//I solved this exception by using @WebMvcTest(MyController.class) at the class level. 
//This annotation will disable full auto-configuration and instead apply only configuration relevant to MVC tests.
//
//An alternative to this is, If you are looking to load your full application configuration and use MockMVC, 
//you should consider @SpringBootTest combined with @AutoConfigureMockMvc rather than @WebMvcTest


//@ComponentScan(basePackages = {"com.typology"})
//@ContextConfiguration(classes = ControllerTests.class)


//if using JUnit 4
//@WebMvcTest				// does not work. this annotation is the problem
//@RunWith(SpringRunner.class)


@SpringBootTest 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
public class ControllerTests
{
	@Autowired
    private MockMvc mockMvc;		//needed to call REST APIs
	
									//actually belongs to Spring rather than Mockito, tells Spring
								    //to create a mock instance of EmployeeService and add it to the application context so that
								    //it's injected into EmployeeController
	@MockBean
    private AppUserService appUserService;
	
	@MockBean
	private EnneagramTypingConsensusService enneagramTypingConsensusService;
	
	@MockBean
    private EntryService entryService;
	
	@MockBean
    private TypingServiceImpl typingService;
	
	@MockBean
	private TypistServiceImpl typistService;
	
    @Autowired						
    private ObjectMapper objectMapper;

    
//    @Test
//    public void givenAppUserObject_whenCreateAppUser_thenReturnSavedAppUser() throws Exception{
//
//        // given - precondition or setup
//    	AppUser appUser = AppUser.builder()
//								 .id(123)
//								 .name("Ibanez")
//								 .pwd("haha")
//								 .registrationTimestamp(ZonedDateTime.now())
//								 .status("enabled")
//								 .build();
//    	
//    	//given(appUserService.saveAppUser(appUser)
//        //        			 .willAnswer((invocation)-> invocation.getArgument(0));	//will return whatever argument you pass to the saveEmployee
//
//    	given(appUserService.saveAppUser(any(AppUser.class)))		//any is ArgumentMatchers.any
//                			.willAnswer((invocation)-> invocation.getArgument(0));	//will return whatever argument you pass to the saveEmployee
//
//        // when - action or behaviour that we are going test
//        //ResultActions since on next step need to verify
//        ResultActions response = mockMvc.perform(post("/api/employees")	//post is MockMvcRequestBuilders.post
//							            .contentType(MediaType.APPLICATION_JSON)					//pass as Json
//							            .content(objectMapper.writeValueAsString(appUser)));		//pass the employee object as Json
//
//        // then - verify the result or output using assert statements
//        //this will validate the response of the REST API
//        response.andDo(print())										//print = MockMvcResultHandlers.print = print the whole response of the REST API
//                .andExpect(status().isCreated())							//verify https status created 201 in a response
//                .andExpect(jsonPath("$.firstName",						//using json path to verify the json values		//jsonPath = MockMvcResultMatchers.jsonPath  //is = CoreMatchers.is
//                        is(appUser.getName())));					
//    }
    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenAppUserObject_whenCreateAppUser_thenReturnSavedAppUser() throws Exception{

    	//need: enneagram typing-> typist, entry -> enneagramTypingConsensus
    	
        // given - precondition or setup
    	Typist typist = Typist.builder()
							  .id(1)
							  .name("Typist person")		  
							  .build();
    	
    	EnneagramTypingConsensus enneagramTypingConsensus = 
    							EnneagramTypingConsensus.builder()
    													.id(1)
													    .coreType(1)
													    .wing(2)
													    .tritypeOrdered(174)
													    .tritypeUnordered(147)
													    .overlay(265)
													    .instinctMain("sp")
													    .instinctStack("sp/sx")
													    .instinctStackFlow("Contraflow")
													    .exInstinctMain("CY")
													    .exInstinctStack("CY/SM/UN")
													    .exInstinctStackAbbreviation("i379")
													    .exInstinctStackFlow("ISP")
													    .build();
    	
    	Entry entry = Entry.builder()
    					   .id(1)
    					   .name("Some character entry")
    					   .category(Category.FICTIONAL_CHARACTER)
    					   .enneagramTypingConsensus(enneagramTypingConsensus)
    					   .typings(new ArrayList<>())
    					   .build();
    	
    	
    	
    	EnneagramTyping enneagramTyping = EnneagramTyping.builder()
		    											.id(1)
		    											.coreType(4)
		    											.entry(entry)
		    											.exInstinctMain("SM")
		    											.exInstinctStack("SM/EX/BG")
		    											.exInstinctStackAbbreviation("i164")
		    											.exInstinctStackFlow("SPI")
		    											.instinctMain("sp")
		    											.instinctStack("sp/so")
		    											.instinctStackFlow("synflow")
		    											.overlay(597)
		    											.tritypeOrdered(486)
		    											.tritypeUnordered(468)
		    											.typist(typist)
		    											.wing(5)
		    											.build();
    	    	
    	given(typistService.saveTypist(typist))
    					   .willAnswer((invocation)-> invocation.getArgument(0));
    	
    	given(enneagramTypingConsensusService.saveEnneagramTypingConsensus(enneagramTypingConsensus))
		   							  		 .willAnswer((invocation)-> invocation.getArgument(0));
    	
    	willDoNothing().given(entry)
		   			   .createEnneagramTyping(entry.getName(), typist, enneagramTyping);
    	
    	willDoNothing().given(typingService)
    				   .createEnneagramTyping(entry.getName(), typist, enneagramTyping);	
    	
    	
    	
    	
//    	given(typingService.saveTyping(any(Typing.class)))		//any is ArgumentMatchers.any
//    	                   .willAnswer((invocation)-> invocation.getArgument(0));			//will return whatever argument you pass to the saveEmployee
//    			
//        // when - action or behaviour that we are going test
//        //ResultActions since on next step need to verify
        ResultActions response = mockMvc.perform(post("/{entryName}/vote/{typologySystem}", entry.getName(), "enneagram")	//post is MockMvcRequestBuilders.post
							            .contentType(MediaType.APPLICATION_JSON)											//pass as Json
							            .content(objectMapper.writeValueAsString(enneagramTyping)));						//pass the employee object as Json

//        // then - verify the result or output using assert statements
//        //this will validate the response of the REST API
        response.andDo(print())												//print = MockMvcResultHandlers.print = print the whole response of the REST API
                .andExpect(status().isCreated())							//verify https status created 201 in a response
                .andExpect(jsonPath("$.id",									//using json path to verify the json values		//jsonPath = MockMvcResultMatchers.jsonPath  //is = CoreMatchers.is
                        is(enneagramTyping.getId())));	
        
//        assertThat(1).isEqualTo(1);
    }
    
    
    
    
    
    
    
 // given - precondition or setup
//	Entry entry = new Entry();
//	entry.setId(0);
//	entry.setCategory(null);
//	entry.setEnneagramTypingConsensus(null);    	
//	entry.setName(null);
//	entry.setTypings(null);
}
