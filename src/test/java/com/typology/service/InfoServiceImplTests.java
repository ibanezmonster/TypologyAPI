package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
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
import com.typology.repository.TypistRepository;
import com.typology.service.impl.InfoServiceImpl;
import com.typology.service.impl.TypingServiceImpl;
import com.typology.service.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InfoServiceImplTests
{
	@Mock
	private TypistRepository typistRepository;
	
	@InjectMocks
	private InfoServiceImpl infoService;
	
	private List<Typist> typists;
	
	//private Typist typist;
	
	@BeforeEach
	public void setup(){
		typistRepository = Mockito.mock(TypistRepository.class);
		infoService = new InfoServiceImpl(typistRepository);		
		
		Typist typist1 = new Typist();
		typist1.setName("typist1");
		
		Typist typist2 = new Typist();
		typist2.setName("typist2");
		
		Typist typist3 = new Typist();
		typist3.setName("typist3");
		
		typists = new ArrayList<>();
		typists.add(typist1);
		typists.add(typist2);
		typists.add(typist3);
	}
	
	
	
	@DisplayName("JUnit service test for get list of typists")
	@Test
	public void givenListOfTypists_whenGetListOfTypistsFromInfoService_thenReturnTypists(){
		
	    // given
		given(typistRepository.findAll())
	    					  .willReturn(typists);
	    
	    // when                
		List<Typist> foundTypists = infoService.getTypists();
	            
	    // then
		assertThat(foundTypists).isNotNull();
	    assertThat(foundTypists).size().isEqualTo(typists.size());
	}
	
	
	
	
	@DisplayName("JUnit service test for get list of typists (negative scenario)")
	@Test
	public void givenNullListOfTypists_whenGetListOfTypistsFromInfoService_thenReturnNothing(){
		
	    // given
		given(typistRepository.findAll())
	    					  .willReturn(null);
	    
	    // when                
		List<Typist> foundTypists = infoService.getTypists();
	            
	    // then
		assertThat(foundTypists).isNull();
	}
}
