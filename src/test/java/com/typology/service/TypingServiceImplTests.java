package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Typist;
import com.typology.repository.AppUserRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.TypingRepository;
import com.typology.service.impl.TypingServiceImpl;
import com.typology.service.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TypingServiceImplTests
{
	@Mock
	private TypingRepository typingRepository;
	
	//@Mock
	//private EnneagramTypingRepository enneagramTypingRepository;
	
	@InjectMocks
	private TypingServiceImpl typingService;
	
	private Typing typing;
	private EnneagramTyping enneagramTyping;	
	private Typist typist;
	private Entry entry;
	private TypologySystem enneagramSystem;
	
	
	@BeforeEach
	public void setup(){
		typingRepository = Mockito.mock(TypingRepository.class);
		typingService = new TypingServiceImpl(typingRepository);
		
		typist = new Typist();
		typist.setName("UFDISUFODS");
		
		entry = new Entry();		
		entry.setName("Some character");					
		
		enneagramSystem = new TypologySystem();
		enneagramSystem.setName("Enneagram");
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(enneagramSystem);
		
		
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
	}
	
	
	
	@DisplayName("JUnit service test for save typing")
	@Test
	public void givenTypingObject_whenSave_thenCreatedResponse(){
		
	    // given
		//unnecessary stubbing
	    //given(typingRepository.save(typing))
	    //					  .willReturn(typing);
	    
	    // when                
		ResponseEntity<String> savedTyping = null;
	    savedTyping = typingService.addTyping(entry.getName(), enneagramSystem.getName(), enneagramTyping);
	            
	    // then
	    assertThat(savedTyping).isNotNull();
	}
	
	
	
	
	@DisplayName("JUnit service test for update typing")
	@Test
	public void givenTypingObject_whenUpdate_thenReturnUpdatedTyping(){
		
	    // given
		//unnecessary stubbing
	    //given(typingRepository.save(typing))
	    //					  .willReturn(typing);
	    
	    // when                
	    ResponseEntity<String> updatedTyping = null;
	    updatedTyping = typingService.updateTyping(entry.getName(), enneagramSystem.getName(), enneagramTyping);
	            
	    // then	    
	    assertThat(updatedTyping).isNotNull();	        	
	}
	
	
	
	//Typing can take various typology systems
	//This one is testing the scenario of enneagram typing specifically
//	@DisplayName("JUnit service test for view typing")
//	@Test
//	public void givenTypingObject_whenSave_thenViewTyping(){
//				
//	    // given
//	    given(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entry.getName()))	    							   
//	    					  		   .willReturn(Optional.of(enneagramTyping));
//	    
//	    // when                
//	    EnneagramTyping foundTyping = (EnneagramTyping) typingService.viewTyping(enneagramTyping.getEntry().getName(), "Enneagram");
//	            
//	    // then
//	    assertThat(foundTyping).isNotNull();
//	    //assertThat(foundTyping).isEqualTo(typing.getEntry().getName());    	
//	}
	
	
	@DisplayName("JUnit service test for delete typing")
	@Test
	public void givenTypingObject_whenDeleted_thenReturnHttpStatusCreated(){
		
		// given
		//unnecessary stubbing
	    //given(typingRepository.save(typing))
	    //	.willReturn(typing);
	    
	    // when                
		ResponseEntity<HttpStatus> deletedTyping = null;
		deletedTyping = typingService.deleteTyping(entry.getName(), enneagramSystem.getName());
					            
	    // then
	    assertThat(deletedTyping).isNotNull();	        	    	
	}
}
