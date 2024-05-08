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
import com.typology.service.impl.EntryServiceImpl;
import com.typology.service.impl.TypingServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT.RANDOM_PORT) 
@AutoConfigureMockMvc(addFilters = false)	//disabling security
@Testcontainers
public class TypingControllerITests extends ContainerStartup
{
	@Autowired
    private MockMvc mockMvc;		

    @Autowired						
    private ObjectMapper objectMapper;
       
    @Autowired
    //@Mock
    private AuthoritiesRepository authoritiesRepository;
    
    @Autowired
    //@Mock
    private EntryRepository entryRepository;
    
    @Autowired
    //@Mock
    private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
    
    @Autowired
    //@Mock
    private TypistRepository typistRepository;
    
    @Autowired
    //@Mock
    private EnneagramTypingRepository enneagramTypingRepository;
        
    @Autowired
    //@Mock
	private TypingRepository typingRepository;
    
    
    @Autowired
   	private ModelMapper modelMapper;
    
    
    @Mock
    //@InjectMocks
	private TypingServiceImpl typingService;
    
    @Mock
    //@InjectMocks
	private EntryServiceImpl entryService;
    
	
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
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenEnneagramTyping_whenAddTyping_thenSaveTyping() throws Exception{

    	//given
		typist = new Typist();
		typist.setName("Newtypist");
		
		entry = new Entry();		
		entry.setName("Somecharacter");					
		
			//using pre-populated value
		enneagramSystem = new TypologySystem();
		enneagramSystem.setId(1);
		enneagramSystem.setName("enneagram");
		
		
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
		enneagramTyping.setExInstinctStackAbbreviation("749");
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(3);
		enneagramTypingConsensus.setWing(2);
		
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		typingRepository.save(typing);
    	
    	// when
		ResultActions response = mockMvc.perform(post("/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), "enneagram")	
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
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenEnneagramTyping_whenUpdateTyping_thenReturnUpdatedTyping() throws Exception{

    	//given
		typist = new Typist();
		typist.setName("Newtypist");
		
		entry = new Entry();		
		entry.setName("Somecharacter");					
		
			//using pre-populated value
		enneagramSystem = new TypologySystem();
		enneagramSystem.setId(1);
		enneagramSystem.setName("enneagram");
		
		
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
		enneagramTyping.setExInstinctStackAbbreviation("749");
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(3);
		enneagramTypingConsensus.setWing(2);
		
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 		
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		typingRepository.save(typing);
		
			//update instinct stack
		enneagramTyping.setInstinctStack("so/sx");
    	
    	// when
		ResultActions response = mockMvc.perform(patch("/api/v1/profile/{entryName}/vote/{typologySystem}", entry.getName(), "enneagram")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8")
							            .content(objectMapper.writeValueAsString(enneagramTyping)));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().string("For entry: " + entry.getName() + ", " + "typing is updated"));
    }
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenEnneagramTyping_whenViewTyping_thenReturnTyping() throws Exception{

    	//given
    	typist = new Typist();
		typist.setName("Newtypist");
		
		entry = new Entry();		
		entry.setName("Somecharacter");					
		
			//using pre-populated value
		enneagramSystem = new TypologySystem();
		enneagramSystem.setId(1);
		enneagramSystem.setName("enneagram");
		
		
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
		enneagramTyping.setExInstinctStackAbbreviation("749");
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(3);
		enneagramTypingConsensus.setWing(2);
		
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	 	
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		typingRepository.save(typing);		
		
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", entry.getName(), "enneagram")	
		//ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", "test123", "enneagram")
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));
							            //.content(objectMapper.writeValueAsString(enneagramTyping)));						
		
	    // then    	
	    response.andDo(print())
	            .andExpect(status().isOk());
	            //.andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName()));
	            //.andExpect(content().string("For entry: " + entry.getName() + ", " + "typing is updated"));
    }
    
    
    
    
    //testing view typing 404
    //ResultActions response = mockMvc.perform(get("/api/v1/profile/{entryName}/my_typing/{typologySystem}", "nonexistent", "enneagram")

    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenEnneagramTyping_whenDeleteTyping_thenDeleteTypingAndReturnNoContent() throws Exception{

    	//given
    	typist = new Typist();
		typist.setName("Newtypist");
		
		entry = new Entry();		
		entry.setName("Somecharacter");					
		
			//using pre-populated value
		enneagramSystem = new TypologySystem();
		enneagramSystem.setId(1);
		enneagramSystem.setName("enneagram");
		
		
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
		enneagramTyping.setExInstinctStackAbbreviation("749");
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(3);
		enneagramTypingConsensus.setWing(2);
		
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 	  	
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		typingRepository.save(typing);		
		
    	// when
		ResultActions response = mockMvc.perform(delete("/api/v1/profile/{entryName}/my_typing/{typologySystem}", entry.getName(), "enneagram")	
							            .contentType(MediaType.APPLICATION_JSON)	
							            .characterEncoding("UTF-8"));						
//		assertThat(1).isEqualTo(1);
		
//	    // then    	
	    response.andDo(print())
	            .andExpect(status().isNoContent());
    }
    
    
    
    
    
    
    
    
    
    @Test
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void givenMultipleTypingsForOneTypist_whenViewAllOfMyTypings_thenReturnTypings() throws Exception{

    	//given
    	List<Typing> typingList = new ArrayList<>();
    	
    	typist = new Typist();
		typist.setName("Newtypist");
		
		
		//create typing 1
		entry = new Entry();		
		entry.setName("Somecharacter");	
		entry.setCategory(Category.FICTIONAL_CHARACTER);
		
		//using pre-populated value
		enneagramSystem = new TypologySystem();
		enneagramSystem.setId(1);
		enneagramSystem.setName("enneagram");
		
		
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
		enneagramTyping.setExInstinctStackAbbreviation("749");
		enneagramTyping.setExInstinctStackFlow("PIS");
		enneagramTyping.setOverlay(369);
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);	
		
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(3);
		enneagramTypingConsensus.setWing(2);
		
		entry.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem); 		
		typing.setCreatedTimestamp(LocalDateTime.now());
		
		
				
		//save typing 1
		typistRepository.save(typist);
		entryRepository.save(entry);
		enneagramTypingRepository.save(enneagramTyping);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		typingRepository.save(typing);
		
		typingList.add(typing);
		
		
		//create typing 2
		Entry entry2;
		EnneagramTyping enneagramTyping2;
		EnneagramTypingConsensus enneagramTypingConsensus2;
		Typing typing2;
		
		entry2 = new Entry();		
		entry2.setName("Somesecondcharacter");	
		entry2.setCategory(Category.BAND);
		
		enneagramTyping2 = new EnneagramTyping();
		enneagramTyping2.setCoreType(5);
		enneagramTyping2.setWing(6);
		enneagramTyping2.setTritypeUnordered(259);
		enneagramTyping2.setTritypeOrdered(592);
		enneagramTyping2.setInstinctMain("sx");
		enneagramTyping2.setInstinctStack("sx/sp");
		enneagramTyping2.setInstinctStackFlow("synflow");
		enneagramTyping2.setExInstinctMain("EX");
		enneagramTyping2.setExInstinctStack("EX/BG/SY");
		enneagramTyping2.setExInstinctStackAbbreviation("649");
		enneagramTyping2.setExInstinctStackFlow("PIS");
		enneagramTyping2.setOverlay(361);
		enneagramTyping2.setEntry(entry2);
		enneagramTyping2.setTypist(typist);	
		
		enneagramTypingConsensus2 = new EnneagramTypingConsensus();
		enneagramTypingConsensus2.setCoreType(7);
		enneagramTypingConsensus2.setWing(8);
		
		entry2.setEnneagramTypingConsensus(enneagramTypingConsensus2);

		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry2);
		typing2.setTypologySystem(enneagramSystem); 	

		
		
		//save typing 2
		entryRepository.save(entry2);
		enneagramTypingRepository.save(enneagramTyping2);
		enneagramTypingConsensusRepository.save(enneagramTypingConsensus2);
		typingRepository.save(typing2);
		
		typingList.add(typing2);
		
    	// when
		ResultActions response = mockMvc.perform(get("/api/v1/profile/my_typings")	
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
}
