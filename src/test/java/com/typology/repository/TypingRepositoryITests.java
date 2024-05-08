package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class TypingRepositoryITests extends ContainerStartup
{
	@Autowired
	private TypingRepository typingRepository;
	
	@Autowired
	private TypistRepository typistRepository;
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private TypologySystemRepository typologySystemRepository;
	
	private Typing typing;
	private Typist typist;
	private Entry entry;
	private TypologySystem mysterySystem;
	private TypologySystem mysterySystem2;

	@BeforeEach
    public void setup(){
		
		typist = new Typist();
		typist.setName("UFDISUFODS");
		
		entry = new Entry();		
		entry.setName("Some character");					
		
		mysterySystem = new TypologySystem();
		//mysterySystem.setId(50);
		mysterySystem.setName("Mystery System");
		
		typing = new Typing();
		typing.setTypist(typist);
		typing.setEntry(entry);
		typing.setTypologySystem(mysterySystem);				
	}
	
	
	@DisplayName("JUnit test for saving typing operation")
    @Test
    public void givenTyping_whenSave_thenReturnSavedTyping(){

        //given
		
    	
        //when
		typistRepository.save(typist);
		entryRepository.save(entry);
		typologySystemRepository.save(mysterySystem);
		
		Typing savedTyping = typingRepository.save(typing);

        // then
        assertThat(savedTyping).isNotNull();
        assertThat(savedTyping.getId()).isGreaterThan(0);
    }
	
	
	@DisplayName("JUnit test for saving typist operation and finding by name")
    @Test
    public void givenTyping_whenSave_thenReturnSavedTypingByName(){

        //given
		mysterySystem2 = new TypologySystem();
		mysterySystem2.setName("Mystery System 2");
		
		Typing typing2 = new Typing();
		
		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry);
		typing2.setTypologySystem(mysterySystem2);		
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		typologySystemRepository.save(mysterySystem);
		typologySystemRepository.save(mysterySystem2);

		//save a typist(user's) mysterySystem typing of one entry
		typingRepository.save(typing);
		
		//save a typist(user's) mysterySystem2 typing of the same entry
		typingRepository.save(typing2);

		
        //when  	
		Optional<List<Typing>> foundTypings = typingRepository.viewAllOfMyTypings(typing.getTypist().getName());

        // then
        assertThat(foundTypings).isNotNull();
        assertThat(foundTypings.get().size()).isEqualTo(2);
    }
	
	
	
	
	
	@DisplayName("JUnit test for returning nothing when finding by nonexistent typings by name")
    @Test
    public void givenNonexistentTypings_whenViewAllOfMyTypings_thenReturnNothing(){

        //given
		mysterySystem2 = new TypologySystem();
		mysterySystem2.setName("Mystery System 2");
		
		Typing typing2 = new Typing();
		
		typing2 = new Typing();
		typing2.setTypist(typist);
		typing2.setEntry(entry);
		typing2.setTypologySystem(mysterySystem2);		
		
		typistRepository.save(typist);
		entryRepository.save(entry);
		typologySystemRepository.save(mysterySystem);
		typologySystemRepository.save(mysterySystem2);

		//don't save these
		//typingRepository.save(typing);		
		//typingRepository.save(typing2);

		
        //when  	
		Optional<List<Typing>> foundTypings = typingRepository.viewAllOfMyTypings(typing.getTypist().getName());

        // then
        assertThat(foundTypings.get().size()).isZero();
    }
}
