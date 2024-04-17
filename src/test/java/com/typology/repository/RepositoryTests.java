package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.typology.entity.user.Authority;
import com.typology.entity.user.Typist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.AppUser;
import com.typology.user.UserDetailsImpl;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private EnneagramTypingRepository enneagramTypingRepository;
    
    @Autowired
    private TypistRepository typistRepository;
    
    private AppUser appUser;
    private Set<Authority> authorities;
    private Authority viewTypings;
    
    
    
    @BeforeEach
    public void setup(){
    	
    	appUser = new AppUser();    	
    	appUser.setId(123);
    	appUser.setName("Ibanez");
    	appUser.setPwd("haha");    	
    	appUser.setRegistrationTimestamp(ZonedDateTime.now());
    	appUser.setStatus("enabled");
    }
    
    
    
    
    // JUnit test for save app user operation
    @DisplayName("JUnit test for creating app user operation")
    @Test
    public void givenAppUserObject_whenSave_thenReturnSavedAppUser(){

        //given
    	//@BeforeEach
    		
        // when  	
    	AppUser savedAppUser = appUserRepository.save(appUser);	

        // then
        assertThat(savedAppUser).isNotNull();
        assertThat(savedAppUser.getId()).isGreaterThan(0);
    }
    

    
    
    // JUnit test for save authority operation
    @DisplayName("JUnit test for creating authority operation")
    @Test
    public void givenAuthorityObject_whenSave_thenReturnSavedAuthority(){

        //given
    	//@BeforeEach
    		
        // when
    	viewTypings = new Authority();
    	viewTypings.setName("VIEWTYPINGS");
    	viewTypings.setId(123L);

        // then
        assertThat(viewTypings).isNotNull();
        assertThat(viewTypings.getId()).isGreaterThan(0);
    }

    
    
    
    
    
    
    
    // JUnit test for save enneagram typing operation
    @DisplayName("JUnit test for save enneagram typing operation")
    @Test
    public void givenEnneagramTypingObject_whenSave_thenReturnSavedEnneagramTyping(){

        //given
    	EnneagramTyping enneagramTyping = new EnneagramTyping();
    		
    	enneagramTyping.setId(1);		
    	enneagramTyping.setCoreType(1);
    	enneagramTyping.setExInstinctMain("UN");
    	enneagramTyping.setExInstinctStack("PIS");
    	enneagramTyping.setInstinctMain("sp");
    	enneagramTyping.setInstinctStack("sp/so");
    	enneagramTyping.setOverlay(784);
    	enneagramTyping.setTritypeOrdered(541);
    	enneagramTyping.setTritypeUnordered(145);
    	

    	// when  
    	EnneagramTyping savedEnneagramTyping = enneagramTypingRepository.save(enneagramTyping);

        // then
        assertThat(savedEnneagramTyping).isNotNull();
        assertThat(savedEnneagramTyping.getId()).isGreaterThan(0);
        assertThat(savedEnneagramTyping.getCoreType()).isEqualTo(1);
        assertThat(savedEnneagramTyping.getExInstinctMain()).isEqualTo("UN");
        assertThat(savedEnneagramTyping.getExInstinctStack()).isEqualTo("PIS");
        assertThat(savedEnneagramTyping.getInstinctMain()).isEqualTo("sp");
        assertThat(savedEnneagramTyping.getInstinctStack()).isEqualTo("sp/so");
        assertThat(savedEnneagramTyping.getOverlay()).isEqualTo(784);
        assertThat(savedEnneagramTyping.getTritypeOrdered()).isEqualTo(541);
        assertThat(savedEnneagramTyping.getTritypeUnordered()).isEqualTo(145);                
    }
    
    
    
    
    
    
    
    // JUnit test for save typist operation
    @DisplayName("JUnit test for save typist operation and retrieve by typist by name")
    @Test
    public void givenTypistObject_whenSave_thenReturnSavedTypistByName(){

        //given
    	Typist typist = new Typist();
    	typist.setName("TestttttTypist");
    		
        // when   	
    	Typist savedTypist = typistRepository.save(typist);

        // then
        assertThat(savedTypist).isNotNull();
        assertThat(savedTypist.getName()).isEqualTo("TestttttTypist");
        assertThat(savedTypist.getId()).isEqualTo(1);
        assertThat(typistRepository.findByName(typist.getName()).isPresent());  
    }
    
    
    
    
    
//    // JUnit test for save typist operation
//    @DisplayName("JUnit test for save typist operation and retrieve by typist by name")
//    @Test
//    public void givenTypistObject_whenSave_thenReturnSavedTypistByName(){
//
//        //given - precondition or setup
//    	//@BeforeEach
//    		
//        // when - action or the behaviour that we are going test    	
//    	Typist typist = new Typist();
//    	typist.setId(111);
//    	typist.setName("Test Typist");    	
//
//        // then - verify the output
//        assertThat(typist).isNotNull();                
//    }
//    
//    
    
    
    
    
    
    
    
//    
//    
//    // JUnit test for save app user operation
//    @DisplayName("JUnit test for save enneagram typing operation and retrieve by typist and entry name")
//    @Test
//    public void givenEnneagramTypingObject_whenSave_thenReturnSavedEnneagramTypingByTypistAndEntryName(){
//
//        //given - precondition or setup
//    	//@BeforeEach
//    		
//        // when - action or the behaviour that we are going test    	
//    	EnneagramTyping savedEnneagramTyping = new EnneagramTyping();
//
//    	savedEnneagramTyping.setId(1);		
//    	savedEnneagramTyping.setCoreType(1);
//    	savedEnneagramTyping.setExInstinctMain("UN");
//    	savedEnneagramTyping.setExInstinctStack("PIS");
//    	savedEnneagramTyping.setInstinctMain("sp");
//    	savedEnneagramTyping.setInstinctStack("sp/so");
//    	savedEnneagramTyping.setOverlay(784);
//    	savedEnneagramTyping.setTritypeOrdered(541);
//    	savedEnneagramTyping.setTritypeUnordered(145);
//
//        // then - verify the output
//        assertThat(savedEnneagramTyping).isNotNull();
//        assertThat(savedEnneagramTyping.getId()).isGreaterThan(0);
//        assertThat(savedEnneagramTyping.getCoreType()).isEqualTo(1);
//        assertThat(savedEnneagramTyping.getExInstinctMain()).isEqualTo("UN");
//        assertThat(savedEnneagramTyping.getExInstinctStack()).isEqualTo("PIS");
//        assertThat(savedEnneagramTyping.getInstinctMain()).isEqualTo("sp");
//        assertThat(savedEnneagramTyping.getInstinctStack()).isEqualTo("sp/so");
//        assertThat(savedEnneagramTyping.getOverlay()).isEqualTo(784);
//        assertThat(savedEnneagramTyping.getTritypeOrdered()).isEqualTo(541);
//        assertThat(savedEnneagramTyping.getTritypeUnordered()).isEqualTo(145);                
//    }
//    
//    
//    
//    // JUnit test for save app user operation
//    @DisplayName("JUnit test for save enneagram typing operation and retrieve by typist and entry name")
//    @Test
//    public void givenEnneagramTypingObject_whenSave_thenReturnSavedEnneagramTypingByTypistAndEntryName(){
//
//        //given - precondition or setup
//    	//@BeforeEach
//    		
//        // when - action or the behaviour that we are going test    	
//    	EnneagramTyping savedEnneagramTyping = new EnneagramTyping();
//
//    	savedEnneagramTyping.setId(1);		
//    	savedEnneagramTyping.setCoreType(1);
//    	savedEnneagramTyping.setExInstinctMain("UN");
//    	savedEnneagramTyping.setExInstinctStack("PIS");
//    	savedEnneagramTyping.setInstinctMain("sp");
//    	savedEnneagramTyping.setInstinctStack("sp/so");
//    	savedEnneagramTyping.setOverlay(784);
//    	savedEnneagramTyping.setTritypeOrdered(541);
//    	savedEnneagramTyping.setTritypeUnordered(145);
//
//        // then - verify the output
//        assertThat(savedEnneagramTyping).isNotNull();
//        assertThat(savedEnneagramTyping.getId()).isGreaterThan(0);
//        assertThat(savedEnneagramTyping.getCoreType()).isEqualTo(1);
//        assertThat(savedEnneagramTyping.getExInstinctMain()).isEqualTo("UN");
//        assertThat(savedEnneagramTyping.getExInstinctStack()).isEqualTo("PIS");
//        assertThat(savedEnneagramTyping.getInstinctMain()).isEqualTo("sp");
//        assertThat(savedEnneagramTyping.getInstinctStack()).isEqualTo("sp/so");
//        assertThat(savedEnneagramTyping.getOverlay()).isEqualTo(784);
//        assertThat(savedEnneagramTyping.getTritypeOrdered()).isEqualTo(541);
//        assertThat(savedEnneagramTyping.getTritypeUnordered()).isEqualTo(145);                
//    }
}
