package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MSSQLServerContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.TestEntity;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.service.AppUserService;
import com.typology.service.EnneagramTypingConsensusService;
import com.typology.service.EntryService;
import com.typology.service.TestService;
import com.typology.service.impl.TestServiceImpl;
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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
public class ControllerTests extends ContainerStartup
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
    
    
    @MockBean
	private TestService testService;
    
    
  
	//static MSSQLServerContainer mssqlserver;
	
	
    @Test
    @WithMockUser(username = "admin", password = "abc123", roles = {"ADMIN"})
    public void testdb() throws Exception{
    	assertThat(1).isEqualTo(1);
    }
	
    
    @Test
    @WithMockUser(username = "admin", password = "abc123", roles = {"ADMIN"})
    public void givenTestObject_whenCreateTest_thenReturnSavedTest() throws Exception{
    	
    	
    	
    	//@Rule
    	//mssqlserver = new MSSQLServerContainer().acceptLicense();

//    	    @Test
//    	    public void someTestMethod() {
//    	        String url = mssqlserver.getJdbcUrl();
    	
    	
    	

    	//need: enneagram typing-> typist, entry -> enneagramTypingConsensus
    	
        // given - precondition or setup
    	TestEntity testEntity = TestEntity.builder()
										  .name("blablablablabla")		  
										  .build();
    	
    	given(testService.saveTestEntity(any(TestEntity.class)))
    					 .willAnswer((invocation)-> invocation.getArgument(0));
    	
//        // when - action or behaviour that we are going test
//        //ResultActions since on next step need to verify
        ResultActions response = mockMvc.perform(post("/test/makenew")	//post is MockMvcRequestBuilders.post
							            .contentType(MediaType.APPLICATION_JSON)					
							            .content(testEntity.toString())
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(testEntity)));			//pass the employee object as Json

//        // then - verify the result or output using assert statements
//        //this will validate the response of the REST API
        
        System.out.println(testEntity.getName());
        
        //if test controller returns the testEntity-> fail: it will return 200 instead of 201. success: it will get "name" property
        //if test controller returns ResponseEntity-> fail: not be able to get the "name" property for verification, success: it will return 201 
        response.andDo(print())							
                .andExpect(status().isCreated());
    }

    
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
    
    
    
    
    
//    
//    // JUnit test for Get All employees REST API
//    @Test
//    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
//        // given - precondition or setup
//        List<Employee> listOfEmployees = new ArrayList<>();
//        listOfEmployees.add(Employee.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build());
//        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());
//        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);	//stubbing getAllEmployees
//
//        // when -  action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(get("/api/employees"));
//
//        // then - verify the output
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.size()",								//$ means all json objects
//                        is(listOfEmployees.size())));				
//
//    }
    
    
    
    
    @Test
    @WithMockUser(username = "admin", password = "abc123", roles = {"USER"})
    public void givenEntryObject_whenCreateEntry_thenReturnSavedEntry() throws Exception{

    	
        // given
    	
    	//need: enneagram typing-> typist, entry -> enneagramTypingConsensus
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
    					   .name("somecharacterentry")
    					   .category(Category.FICTIONAL_CHARACTER)
    					   .build();
    	
    		    	
    	given(typistService.saveTypist(typist))
    					   .willAnswer((invocation)-> invocation.getArgument(0));
    	
    	given(enneagramTypingConsensusService.saveEnneagramTypingConsensus(enneagramTypingConsensus))
		   							  		 .willAnswer((invocation)-> invocation.getArgument(0));
    	
    	given(entryService.saveEntry(entry))
	  		 			  .willAnswer((invocation)-> invocation.getArgument(0));
    	

        // when
        ResultActions response = mockMvc.perform(post("/console/add_entry")	
							            .contentType(MediaType.APPLICATION_JSON)
							            .characterEncoding("UTF-8")					
							            .content(entry.toString())
							            .content(objectMapper.writeValueAsString(entry)));

       // then
        response.andDo(print())												
                .andExpect(status().isCreated());							
    }
    
    
    
    
    
    
    
    
    
    
    
//    @Test
//    @WithMockUser(username = "test", password = "test", roles = {"USER"})
//    public void givenEnneagramTypingObject_whenCreateEnneagramTyping_thenReturnSavedEnneagramTyping() throws Exception{
//
//    	//need: enneagram typing-> typist, entry -> enneagramTypingConsensus
//    	
//        // given - precondition or setup
//    	Typist typist = Typist.builder()
//							  .id(1)
//							  .name("Typist person")		  
//							  .build();
//    	
//    	EnneagramTypingConsensus enneagramTypingConsensus = 
//    							EnneagramTypingConsensus.builder()
//    													.id(1)
//													    .coreType(1)
//													    .wing(2)
//													    .tritypeOrdered(174)
//													    .tritypeUnordered(147)
//													    .overlay(265)
//													    .instinctMain("sp")
//													    .instinctStack("sp/sx")
//													    .instinctStackFlow("Contraflow")
//													    .exInstinctMain("CY")
//													    .exInstinctStack("CY/SM/UN")
//													    .exInstinctStackAbbreviation("i379")
//													    .exInstinctStackFlow("ISP")
//													    .build();
//    	
//    	Entry entry = Entry.builder()
//    					   .id(1)
//    					   .name("somecharacterentry")
//    					   .category(Category.FICTIONAL_CHARACTER)
//    					   .enneagramTypingConsensus(enneagramTypingConsensus)
//    					   .typings(new ArrayList<>())
//    					   .build();
//    	
//    	
//    	
//    	EnneagramTyping enneagramTyping = EnneagramTyping.builder()
//		    											.id(1)
//		    											.coreType(4)
//		    											.entry(entry)
//		    											.exInstinctMain("SM")
//		    											.exInstinctStack("SM/EX/BG")
//		    											.exInstinctStackAbbreviation("i164")
//		    											.exInstinctStackFlow("SPI")
//		    											.instinctMain("sp")
//		    											.instinctStack("sp/so")
//		    											.instinctStackFlow("synflow")
//		    											.overlay(597)
//		    											.tritypeOrdered(486)
//		    											.tritypeUnordered(468)
//		    											.typist(typist)
//		    											.wing(5)
//		    											.build();
//    	    	
//    	given(typistService.saveTypist(typist))
//    					   .willAnswer((invocation)-> invocation.getArgument(0));
//    	
//    	given(enneagramTypingConsensusService.saveEnneagramTypingConsensus(enneagramTypingConsensus))
//		   							  		 .willAnswer((invocation)-> invocation.getArgument(0));
//    	
//    	given(entryService.saveEntry(entry))
//    					  .willAnswer((invocation)-> invocation.getArgument(0));
//    	
//    	willDoNothing().given(typingService)
//    				   .createEnneagramTyping(entry.getName(), typist, enneagramTyping);	
//    	
//    	//BDDMockito.when(entryService.saveEntry(entry))
//    	//				 .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
//
//    	
//    	
////    	given(typingService.saveTyping(any(Typing.class)))		//any is ArgumentMatchers.any
////    	                   .willAnswer((invocation)-> invocation.getArgument(0));			//will return whatever argument you pass to the saveEmployee
////    			
////        // when - action or behaviour that we are going test
////        //ResultActions since on next step need to verify
//        ResultActions response = mockMvc.perform(post("http://localhost:8080/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), "enneagram")	//post is MockMvcRequestBuilders.post
//							            .contentType(MediaType.APPLICATION_JSON)											//pass as Json
//							            .content(objectMapper.writeValueAsString(enneagramTyping)));						//pass the employee object as Json
//
////        // then - verify the result or output using assert statements
////        //this will validate the response of the REST API
//        response.andDo(print())												//print = MockMvcResultHandlers.print = print the whole response of the REST API
//                .andExpect(status().isCreated())							//verify https status created 201 in a response
//                .andExpect(jsonPath("$.id",									//using json path to verify the json values		//jsonPath = MockMvcResultMatchers.jsonPath  //is = CoreMatchers.is
//                        is(enneagramTyping.getId())));	
//        
////        assertThat(1).isEqualTo(1);
//    }
//    
//    
//    
  
}
