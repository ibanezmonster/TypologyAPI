package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.MyTypingsDTO;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.repository.AppUserRepository;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;
import com.typology.repository.TypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.repository.TypologySystemRepository;
import com.typology.service.impl.TypingServiceImpl;
import com.typology.service.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TypingServiceImplTests
{
	@Mock
	private TypistRepository typistRepository;
	
	@Mock
	private EntryRepository entryRepository;
	
	@Mock
	private EnneagramTypingRepository enneagramTypingRepository;
	
	@Mock
	private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	
	@Mock
	private TypingRepository typingRepository;
	
	@Mock
	private TypologySystemRepository typologySystemRepository;
	
	@Mock
	MyTypingsDTO myTypingsDTO;
	
	@InjectMocks
	private TypingServiceImpl typingService;
	
	
	//typing 1
	private Typing typing;
	private EnneagramTyping enneagramTyping;
	private EnneagramTypingConsensus enneagramTypingConsensus;
	private Typist typist;
	private Entry entry;
	private TypologySystem enneagramSystem;
	
	//typing 2
	private Typing typing2;
	private EnneagramTyping enneagramTyping2;
	private EnneagramTypingConsensus enneagramTypingConsensus2;	
	private Entry entry2;
	
	
	private List<Typing> typingList;
	
	@BeforeEach
	public void setup(){
		//mocks
		typistRepository = Mockito.mock(TypistRepository.class);
		entryRepository = Mockito.mock(EntryRepository.class);
		enneagramTypingRepository = Mockito.mock(EnneagramTypingRepository.class);
		enneagramTypingConsensusRepository = Mockito.mock(EnneagramTypingConsensusRepository.class);
		typingRepository = Mockito.mock(TypingRepository.class);
		typologySystemRepository = Mockito.mock(TypologySystemRepository.class);

		//inject mock
		//typingService = new TypingServiceImpl(typingRepository);
		typingService = new TypingServiceImpl(typingRepository, entryRepository, enneagramTypingRepository, 
												enneagramTypingConsensusRepository, typistRepository, typologySystemRepository);
		
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
	}
	
	
	//use this ordering

	//given(typistRepository.save(typist))
	//   					.willReturn(typist);
	
	//given(typologySystemRepository.save(enneagramSystem))
	//   					        .willReturn(enneagramSystem);
	
	//given(entryRepository.save(entry))
	//	  				   .willReturn(entry);
	
    //given(enneagramTypingRepository.save(enneagramTyping))
    //					  		     .willReturn(enneagramTyping);
    
    //given(enneagramTypingConsensusRepository.save(enneagramTypingConsensus))
	//  									  .willReturn(enneagramTypingConsensus);
    
    //given(typingRepository.save(typing))
	//	  				    .willReturn(typing);
    
	

	
	@DisplayName("JUnit service test for view all of my typings")
	@Test
	public void givenUserAsTypist_whenViewAllOfMyTypings_thenDisplayAllOfMyTypings(){
				
	    // given
		//extra typing to make 2 in list
		entry2 = new Entry();		
		entry2.setName("Somecharacter2");					
		
		enneagramTyping2 = new EnneagramTyping();
		enneagramTyping2.setCoreType(8);
		enneagramTyping2.setWing(9);
		enneagramTyping2.setTritypeUnordered(468);
		enneagramTyping2.setTritypeOrdered(684);
		enneagramTyping2.setInstinctMain("so");
		enneagramTyping2.setInstinctStack("so/sp");
		enneagramTyping2.setInstinctStackFlow("synflow");
		enneagramTyping2.setExInstinctMain("UN");
		enneagramTyping2.setExInstinctStack("UN/CY/SY");
		enneagramTyping2.setExInstinctStackAbbreviation("739");
		enneagramTyping2.setExInstinctStackFlow("PIS");
		enneagramTyping2.setOverlay(369);
		enneagramTyping2.setEntry(entry2);
		enneagramTyping2.setTypist(typist);	
		
		enneagramTypingConsensus2 = new EnneagramTypingConsensus();
		enneagramTypingConsensus2.setCoreType(5);
		enneagramTypingConsensus2.setWing(6);
		
		entry2.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry2);
		typing2.setTypologySystem(enneagramSystem); 	
		
		//create list
		typingList = new ArrayList<>();
		typingList.add(typing);
		typingList.add(typing2);
		
	    given(typingRepository.viewAllOfMyTypings(typist.getName()))	    							   
	    					  .willReturn(Optional.of(typingList));
	    
	    // when                
	    ResponseEntity<?> typingListResponse = null;
	    
		try {
			typingListResponse = typingService.viewAllOfMyTypings();
		}
		
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	            

	    // then
	    assertThat(typingListResponse.getBody()).isNotNull();
	    assertThat(typingListResponse.getStatusCode().is2xxSuccessful());
	    assertThat(typingListResponse.getBody()
	    							 .toString()
	    							 .contains(typingList.get(1).getEntry()
	    									 					.getName()));   	
	}
	
	
	
	
	
	
	@DisplayName("JUnit service test for view all of my typings (negative scenario)")
	@Test
	public void givenUserAsTypist_whenViewAllOfMyNonexistentTypings_thenDisplayNothing(){
				
	    // given
		//extra typing to make 2 in list
		entry2 = new Entry();		
		entry2.setName("Somecharacter2");					
		
		enneagramTyping2 = new EnneagramTyping();
		enneagramTyping2.setCoreType(8);
		enneagramTyping2.setWing(9);
		enneagramTyping2.setTritypeUnordered(468);
		enneagramTyping2.setTritypeOrdered(684);
		enneagramTyping2.setInstinctMain("so");
		enneagramTyping2.setInstinctStack("so/sp");
		enneagramTyping2.setInstinctStackFlow("synflow");
		enneagramTyping2.setExInstinctMain("UN");
		enneagramTyping2.setExInstinctStack("UN/CY/SY");
		enneagramTyping2.setExInstinctStackAbbreviation("739");
		enneagramTyping2.setExInstinctStackFlow("PIS");
		enneagramTyping2.setOverlay(369);
		enneagramTyping2.setEntry(entry2);
		enneagramTyping2.setTypist(typist);	
		
		enneagramTypingConsensus2 = new EnneagramTypingConsensus();
		enneagramTypingConsensus2.setCoreType(5);
		enneagramTypingConsensus2.setWing(6);
		
		entry2.setEnneagramTypingConsensus(enneagramTypingConsensus);

		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry2);
		typing2.setTypologySystem(enneagramSystem); 	
		
		//create list
		typingList = new ArrayList<>();
		typingList.add(typing);
		typingList.add(typing2);
		
	    given(typingRepository.viewAllOfMyTypings(typist.getName()))	    							   
	    					  .willReturn(Optional.empty());
	    
	    // when                
	    ResponseEntity<?> typingListResponse = null;
	    
		try {
			typingListResponse = typingService.viewAllOfMyTypings();
		}
		
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	            

	    // then
	    assertThat(typingListResponse.getStatusCode().is4xxClientError());
	    assertThat(typingListResponse.getBody()
	    							 .toString()
	    							 .equalsIgnoreCase("No typings found"));
	}
	
	
	
	
	
	
	
	@DisplayName("JUnit service test for view enneagram typing")
	@Test
	public void givenUserAsTypist_whenViewEnneagramTyping_thenDisplayTyping(){
				
	    // given
	    given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))	    							   
	    					  		   .willReturn(Optional.of(enneagramTyping));
	    
	    // when                
	    ResponseEntity<?> foundTyping = null;

		try {
			foundTyping = typingService.viewTyping(enneagramTyping.getEntry().getName(), enneagramSystem.getName());
		}
		
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	            
	    // then
	    assertThat(foundTyping).isNotNull();
	    assertThat(foundTyping.getStatusCode().is2xxSuccessful());
	    assertThat(foundTyping.getBody()
				 			  .toString()
				 			  .contains(entry.getName()));   	
	}
	
	
	
	
	
	
	@DisplayName("JUnit service test for view enneagram typing (negative scenario)")
	@Test
	public void givenUserAsTypist_whenViewNonexistentEnneagramTyping_thenDisplayNothing(){
				
		// given
	    given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))	    							   
	    					  		   .willReturn(Optional.empty());
	    
	    // when                
	    ResponseEntity<?> foundTyping = null;

		try {
			foundTyping = typingService.viewTyping(enneagramTyping.getEntry().getName(), enneagramSystem.getName());
		}
		
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	            
	    // then	    
	    assertThat(foundTyping.getStatusCode().is4xxClientError());
	    assertThat(foundTyping.getBody()
				 			  .toString()
				 			  .equalsIgnoreCase("Typing for: " + entry.getName() + " not found."));     	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@DisplayName("JUnit service test for save enneagram typing")
	@Test
	public void givenEnneagramTypingObject_whenAddTyping_thenReturnSuccessResponse(){
		
	    // given		
		//typist exists
		given(typistRepository.findByName(typist.getName()))
		   					  .willReturn(Optional.of(typist));

		//typing does not already exist for typist's enneagram typing of entry (because this is an add, not update)
		given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))
	    					  		   .willReturn(Optional.empty());
		
		//entry exists
		given(entryRepository.findByName(entry.getName()))
			  				 .willReturn(Optional.of(entry));
		
		//save enneagram typing
		given(enneagramTypingRepository.save(enneagramTyping))
		   							   .willReturn(enneagramTyping);
		
		//typology system exists
		given(typologySystemRepository.findByName(enneagramSystem.getName()))
			  						  .willReturn(Optional.of(enneagramSystem));
		
		//save full typing
	    given(typingRepository.save(typing))
			  				  .willReturn(typing);
		
	    // when                
		ResponseEntity<String> savedTyping = null;
	    savedTyping = typingService.addTyping(entry.getName(), enneagramSystem.getName(), enneagramTyping);
	   
	            
	    // then
	    assertThat(savedTyping).isNotNull();
	    assertThat(savedTyping.getStatusCode().is2xxSuccessful());
	}
	
	
	
	@DisplayName("JUnit service test for update enneagram typing")
	@Test
	public void givenEnneagramTypingObject_whenUpdate_thenReturnSuccessResponse(){
		
	    // given
		//typist exists
		given(typistRepository.findByName(typist.getName()))
		   					  .willReturn(Optional.of(typist));

		//typing does not already exist for typist's enneagram typing of entry (because this is an add, not update)
		given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))
	    					  		   .willReturn(Optional.empty());
    
	    // when                
	    ResponseEntity<String> updatedTyping = null;
	    updatedTyping = typingService.updateTyping(entry.getName(), enneagramSystem.getName(), enneagramTyping);
	            
	    // then	    
	    assertThat(updatedTyping).isNotNull();
	    assertThat(updatedTyping.getStatusCode().is2xxSuccessful());
	}
	
	
	

	
	
	@DisplayName("JUnit service test for delete enneagram typing")
	@Test
	public void givenEnneagramTypingObject_whenDeleted_thenReturnSuccessResponse(){
		
		// given
		//typist exists
		given(typistRepository.findByName(typist.getName()))
		   					  .willReturn(Optional.of(typist));
		
		//typing exists
		given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))
			  						   .willReturn(Optional.of(enneagramTyping));
		
		
		willDoNothing().given(enneagramTypingRepository).deleteById(enneagramTyping.getId());
	    
	    // when                
		ResponseEntity<HttpStatus> deletedTyping = typingService.deleteTyping(entry.getName(), enneagramSystem.getName());
					            
	    // then
        verify(enneagramTypingRepository, times(1)).deleteById(enneagramTyping.getId());
	    assertThat(deletedTyping.getStatusCode().is2xxSuccessful());
	}
}
