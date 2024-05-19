package com.typology.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.RegistrationDTO;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;
import com.typology.repository.TypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.repository.TypologySystemRepository;
import com.typology.service.impl.EntryServiceImpl;
import com.typology.service.impl.TypingServiceImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class TypingControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		

    @Autowired						
    private ObjectMapper objectMapper;
       
    @Autowired
    private EntryRepository entryRepository;
    
    @Autowired
    private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
    
    @Autowired
    private TypistRepository typistRepository;
    
    @Autowired
    private TypologySystemRepository typologySystemRepository;
    
    @Autowired
    private EnneagramTypingRepository enneagramTypingRepository;
        
    @Autowired
	private TypingRepository typingRepository;
    
    //@Mock
    //@InjectMocks
    //@Autowired
	//private TypingServiceImpl typingService;
    
    //@Mock
    //@InjectMocks
    //@Autowired
	//private EntryServiceImpl entryService;
    
	
	private Typing typing;

	private EnneagramTyping enneagramTyping;
	private EnneagramTypingConsensus enneagramTypingConsensus;
	private Typist typist;
	private Entry entry;
	private TypologySystem enneagramSystem;
      
	
	
    @BeforeEach
   	void setup() {
    	//typistRepository.deleteAll();  
    	
   	}
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenEnneagramTyping_whenAddTyping_thenSaveTyping() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);		
		entryRepository.save(entry);
		
		//don't save
		//enneagramTypingRepository.save(enneagramTyping);
		//typingRepository.save(typing);			
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(enneagramTyping)));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isCreated())
	            .andExpect(content().string("For entry: " + entry.getName() + ", " + " new typing is created"));
    }
    
    
    
    
    
    
    
    
    
    
    
//    @Test
//    @WithMockUser(username = "test", password = "test", roles = {"USER"})
//    public void givenEnneagramTyping_whenUpdateTyping_thenReturnUpdatedTyping() throws Exception{
//
//    	//given
//    	typingRepository = Mockito.mock(TypingRepository.class);
//		typingService = new TypingServiceImpl(typingRepository);
//		
//		typist = new Typist();
//		typist.setName("UFDISUFODS");
//		
//		entry = new Entry();		
//		entry.setName("Some character");					
//		
//		enneagramSystem = new TypologySystem();
//		enneagramSystem.setName("Enneagram");
//		
//		
//		enneagramTyping = new EnneagramTyping();
//		enneagramTyping.setCoreType(7);
//		enneagramTyping.setWing(8);
//		enneagramTyping.setTritypeUnordered(478);
//		enneagramTyping.setTritypeOrdered(784);
//		enneagramTyping.setInstinctMain("so");
//		enneagramTyping.setInstinctStack("so/sp");
//		enneagramTyping.setInstinctStackFlow("synflow");
//		enneagramTyping.setExInstinctMain("UN");
//		enneagramTyping.setExInstinctStack("UN/BG/SY");
//		enneagramTyping.setExInstinctStackAbbreviation("749");
//		enneagramTyping.setExInstinctStackFlow("PIS");
//		enneagramTyping.setOverlay(369);
//		enneagramTyping.setEntry(entry);
//		enneagramTyping.setTypist(typist);	
//
//		typing = Typing.builder()
//					   .typist(typist)
//					   .entry(entry)
//					   .typologySystem(enneagramSystem)
//					   .build();  	
//		
//		//entryService.saveEntry(entry);
//		given(entryRepository.save(entry))
//							 .willReturn(entry);
//		
//		given(typingRepository.save(typing))
//		 					  .willReturn(typing);	
//		
//			//update instinct stack
//		enneagramTyping.setInstinctStack("so/sx");
//    	
//    	// when
//		ResultActions response = mockMvc.perform(patch("/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), "enneagram")	
//							            .contentType(MediaType.APPLICATION_JSON)	
//							            .characterEncoding("UTF-8")
//							            .content(objectMapper.writeValueAsString(enneagramTyping)));						
//		
//	    // then    	
//	    response.andDo(print())
//	            .andExpect(status().isOk())
//	            .andExpect(content().string("For entry: " + entry.getName() + ", " + "typing is updated"));
//    }
//    
//    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenEnneagramTyping_whenUpdateTyping_thenReturnUpdatedTyping() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		
		enneagramTypingRepository.save(enneagramTyping);
		typingRepository.save(typing);
		
			//update instinct stack
		enneagramTyping.setInstinctStack("so/sx");
    	
    	// when
		ResultActions response = mockMvc.perform(patch("/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(enneagramTyping)));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().string("For entry: " + entry.getName() + ", " + "typing is updated"));
    }
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenEnneagramTyping_whenViewTyping_thenReturnTyping() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		
		enneagramTypingRepository.save(enneagramTyping);
		typingRepository.save(typing);			
		
	
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", entry.getName(), enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk());
	            //.andExpect(jsonPath("$.coreType", is(enneagramTyping.getCoreType())))
	            //.andExpect(jsonPath("$.instinctStack", is(enneagramTyping.getInstinctStack())));
    }
    
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenNoEnneagramTypingForEntry_whenViewTyping_thenReturnEmpty() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		
		
		//enneagramTypingRepository.save(enneagramTyping);
		//typingRepository.save(typing);		
		
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", entry.getName(), enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isNotFound())
	            .andExpect(content().string("Typing for: " + entry.getName() + " not found."));;
    }
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenNonexistentEntry_whenViewTyping_thenReturn404NotFound() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		typingRepository.save(typing);
		
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", "nonexistententry", enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isNotFound());
    }
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenEnneagramTyping_whenDeleteTyping_thenDeleteTypingAndReturnNoContent() throws Exception{

    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		typingRepository.save(typing);	
		
    	// when
		ResultActions response = mockMvc.perform(delete("/api/v1/profile/{entryName}/my_typing/{typologySystem}", entry.getName(), enneagramSystem.getName())	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isNoContent());
    }
    
    
    
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenMultipleTypingsForOneTypist_whenViewAllOfMyTypings_thenReturnTypings() throws Exception{

    	//given
    	List<Typing> typingList = new ArrayList<>();
    	
    	//given
    	typist = new Typist();    	
		typist.setName("Newtypist");
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("enneagram");
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(5);
		enneagramTypingConsensus.setWing(6);
		enneagramTypingConsensus.setTritypeOrdered(592);
		enneagramTypingConsensus.setTritypeOrdered(259);
		enneagramTypingConsensus.setOverlay(613);
		enneagramTypingConsensus.setInstinctMain("so");
		enneagramTypingConsensus.setInstinctStack("so/sp");
		enneagramTypingConsensus.setExInstinctMain("CY");
		enneagramTypingConsensus.setExInstinctStackFlow("CY/EX/SY");


		entry = new Entry();
		entry.setName("Somecharacter");		
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		enneagramTyping = new EnneagramTyping();
		enneagramTyping.setCoreType(7);
		enneagramTyping.setWing(8);
		enneagramTyping.setTritypeUnordered(478);
		enneagramTyping.setTritypeOrdered(784);
		enneagramTyping.setInstinctMain("so");
		enneagramTyping.setInstinctStack("so/sp");
		enneagramTyping.setInstinctStackFlow("synflow");
		enneagramTyping.setExInstinctMain("UN");
		enneagramTyping.setExInstinctStack("UN/BG/SY");
		enneagramTyping.setExInstinctStackAbbreviation(749);
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		

		//save
		typologySystemRepository.save(enneagramSystem);
		typistRepository.save(typist);		
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		typingRepository.save(typing);
		
		typingList.add(typing);
		
		
		//create typing 2
		Entry entry2;
		EnneagramTyping enneagramTyping2;
		EnneagramTypingConsensus enneagramTypingConsensus2;
		Typing typing2;
		
		//given
		enneagramTypingConsensus2 = new EnneagramTypingConsensus();
		enneagramTypingConsensus2.setCoreType(6);
		enneagramTypingConsensus2.setWing(7);
		enneagramTypingConsensus2.setTritypeOrdered(692);
		enneagramTypingConsensus2.setTritypeOrdered(269);
		enneagramTypingConsensus2.setOverlay(613);
		enneagramTypingConsensus2.setInstinctMain("so");
		enneagramTypingConsensus2.setInstinctStack("so/sp");
		enneagramTypingConsensus2.setExInstinctMain("CY");
		enneagramTypingConsensus2.setExInstinctStackFlow("CY/EX/SY");


		entry2 = new Entry();
		entry2.setName("Somecharacter2");		
		entry2.setCategory(Category.FICTIONAL_CHARACTER);
		entry2.setEnneagramTypingConsensus(enneagramTypingConsensus2);

		enneagramTyping2 = new EnneagramTyping();
		enneagramTyping2.setCoreType(6);
		enneagramTyping2.setWing(8);
		enneagramTyping2.setTritypeUnordered(468);
		enneagramTyping2.setTritypeOrdered(684);
		enneagramTyping2.setInstinctMain("so");
		enneagramTyping2.setInstinctStack("so/sp");
		enneagramTyping2.setInstinctStackFlow("synflow");
		enneagramTyping2.setExInstinctMain("UN");
		enneagramTyping2.setExInstinctStack("UN/BG/SY");
		enneagramTyping2.setExInstinctStackAbbreviation(749);
		enneagramTyping2.setExInstinctStackFlow("PIS");
		enneagramTyping2.setOverlay(359);
		enneagramTyping2.setEntry(entry2);
		enneagramTyping2.setTypist(typist);	
		
		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry2);
		typing2.setTypologySystem(enneagramSystem); 	 	
		

		//save
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus2);
		entryRepository.save(entry2);
		enneagramTypingRepository.save(enneagramTyping2);
		typingRepository.save(typing2);
		
		typingList.add(typing2);
		
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/my_typings")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));						
		
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.size()", is(typingList.size())))
	            .andExpect(jsonPath("$[0].entry", is(typingList.get(0).getEntry().getName())))
	            .andExpect(jsonPath("$[1].entry", is(typingList.get(1).getEntry().getName())))
	            .andExpect(jsonPath("$[0].typologySystem", is(typingList.get(0).getTypologySystem().getName())))
	            .andExpect(jsonPath("$[1].typologySystem", is(typingList.get(1).getTypologySystem().getName())));
    }
    
    
    
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "Newtypist", password = "test", authorities = "ADDTYPINGS")
    public void givenNoTypingsForOneTypist_whenViewAllOfMyTypings_thenReturn404EmptyList() throws Exception{

    	//given
    	
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/my_typings")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk())
	    		.andExpect(content().string("No typings found"));
    }
}
