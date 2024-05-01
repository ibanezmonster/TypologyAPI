package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.service.impl.EnneagramTypingConsensusServiceImpl;
import com.typology.service.impl.EnneagramTypingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EnneagramTypingServiceImplTests
{
	@Mock
	private EnneagramTypingRepository enneagramTypingRepository;
	
	@InjectMocks
	private EnneagramTypingServiceImpl enneagramTypingService;
	
	private EnneagramTyping enneagramTyping;
	
	
	@BeforeEach
	public void setup(){
		enneagramTypingRepository = Mockito.mock(EnneagramTypingRepository.class);
		enneagramTypingService = new EnneagramTypingServiceImpl(enneagramTypingRepository);
			
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
	}
	
	
	
	 // JUnit test for saveAppUser method
	@DisplayName("JUnit service test for save enneagram typing")
	@Test
	public void givenEnneagramTypingObject_whenSave_thenReturnSavedEnneagramTyping(){
		
	    // given
	    given(enneagramTypingRepository.save(enneagramTyping))
	    					   		   .willReturn(enneagramTyping);
	    
	    // when                
	    EnneagramTyping savedEnneagramTyping = enneagramTypingService.saveEnneagramTyping(enneagramTyping);
	            
	    // then
	    assertThat(savedEnneagramTyping).isNotNull();
	    assertThat(savedEnneagramTyping.getCoreType()).isEqualTo(enneagramTyping.getCoreType());    	
	}
}
