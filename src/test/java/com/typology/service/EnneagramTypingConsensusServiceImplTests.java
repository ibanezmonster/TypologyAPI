package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.service.impl.AppUserServiceImpl;
import com.typology.service.impl.EnneagramTypingConsensusServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EnneagramTypingConsensusServiceImplTests
{
	@Mock
	private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	
	@InjectMocks
	private EnneagramTypingConsensusServiceImpl enneagramTypingConsensusService;
	
	private EnneagramTypingConsensus enneagramTypingConsensus;
	
	
	@BeforeEach
	public void setup(){
		 enneagramTypingConsensusRepository = Mockito.mock(EnneagramTypingConsensusRepository.class);
		 enneagramTypingConsensusService = new EnneagramTypingConsensusServiceImpl(enneagramTypingConsensusRepository);
			
		 enneagramTypingConsensus = EnneagramTypingConsensus.builder()
				 											.coreType(4)
				 											.wing(5)
				 											.build();
	}
	
	
	
	 // JUnit test for saveAppUser method
	@DisplayName("JUnit service test for save enneagram typing consensus")
	@Test
	public void givenAppUserObject_whenSave_thenReturnSavedAppUser(){
		
	    // given
	    given(enneagramTypingConsensusRepository.save(enneagramTypingConsensus))
	    					   					.willReturn(enneagramTypingConsensus);
	    
	    // when                
	    EnneagramTypingConsensus savedEnneagramTypingConsensus = enneagramTypingConsensusService.saveEnneagramTypingConsensus(enneagramTypingConsensus);
	            
	    // then
	    assertThat(savedEnneagramTypingConsensus).isNotNull();
	    assertThat(savedEnneagramTypingConsensus.getCoreType()).isEqualTo(enneagramTypingConsensus.getCoreType());    	
	}
}
