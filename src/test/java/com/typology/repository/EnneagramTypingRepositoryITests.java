package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class EnneagramTypingRepositoryITests extends ContainerStartup
{
	@Autowired
	private EnneagramTypingRepository enneagramTypingRepository;
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private TypistRepository typistRepository;
	
	private EnneagramTyping enneagramTyping;
	private Entry entry;
	private Typist typist;
	
	@BeforeEach
    public void setup(){

		entry = new Entry();		
		entry.setId(789);
		entry.setName("Some character");	
		
		typist = new Typist();
		typist.setId(110);
		typist.setName("UFDISUFODS");
				
		enneagramTyping = EnneagramTyping.builder()
										 .coreType(7)
										 .wing(8)
										 .tritypeUnordered(784)
										 .entry(entry)
										 .typist(typist)
										 .build();
    }
	
	@DisplayName("JUnit test for saving enneagram typing operation")
    @Test
    public void givenEnneagramTyping_whenSave_thenReturnSavedEnneagramTyping(){

        //given
    	
        //when  	
		EnneagramTyping savedEnneagramTyping = enneagramTypingRepository.save(enneagramTyping);

        // then
        assertThat(savedEnneagramTyping).isNotNull();
        assertThat(savedEnneagramTyping.getId()).isGreaterThan(0);
    }
	
	
	@DisplayName("JUnit test for saving enneagram typing operation and finding by typist and entry name")
    @Test
    public void givenEnneagramTyping_whenSave_thenFindEnneagramTypingByTypistAndEntryName(){

        //given
    	
        //when  			
		entryRepository.save(entry);
		typistRepository.save(typist);
		enneagramTypingRepository.save(enneagramTyping);

		Optional<EnneagramTyping> foundEnneagramTyping = enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName("Typist Person", "Some character");

        // then
		//assertThat(1).isEqualTo(1);
        assertThat(foundEnneagramTyping.get()).isNotNull();
        assertThat(foundEnneagramTyping.get().getEntry()).isEqualTo(entry.getName());
        assertThat(foundEnneagramTyping.get().getTypist()).isEqualTo(typist.getName());
    }
}
