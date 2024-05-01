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
import org.springframework.http.ResponseEntity;

import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;
import com.typology.repository.TypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.service.impl.InfoServiceImpl;
import com.typology.service.impl.TypingServiceImpl;
import com.typology.service.impl.TypistServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TypistServiceImplTests
{
	@Mock
	private TypistRepository typistRepository;
	
	@InjectMocks
	private TypistServiceImpl typistService;
	
	private Typist typist;
	
	@BeforeEach
	public void setup(){
		typistRepository = Mockito.mock(TypistRepository.class);
		typistService = new TypistServiceImpl(typistRepository);
		
		typist = new Typist();
		typist.setName("UFDISUFODS");		
	}
	
	
	
	@DisplayName("JUnit service test for save typist")
	@Test
	public void givenTypistObject_whenSave_thenReturnTypist(){
		
	    // given
		//unnecessary stubbing
	    given(typistRepository.save(typist))
	    					  .willReturn(typist);
	    
	    // when                
		Typist savedTypist = typistService.saveTypist(typist);
	            
	    // then
	    assertThat(savedTypist).isNotNull();
	    assertThat(savedTypist.getName()).isEqualTo(typist.getName());	    		
	}
}
