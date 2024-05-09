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

import jakarta.transaction.Transactional;



//Note: make sure that Generated values is set to IDENTITY for both the mapped superclass and the EnneagramTyping subclass
//if it isn't, it will either a) not run the insert on save (you can force with saveAndFlush), 
//or b) throw IDENTITY exception error if using saveAndFlush, or when grouping it as a transaction before another entity is being saved



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
		entry.setName("Some character");	
		
		typist = new Typist();
		typist.setName("Typist Person");
					
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
	
	@DisplayName("JUnit test for saving enneagram typing operation")
    @Test
    public void givenEnneagramTyping_whenSave_thenReturnSavedEnneagramTyping(){

        //given
    	
        //when
		entryRepository.save(entry);
		typistRepository.save(typist);
		EnneagramTyping savedEnneagramTyping = enneagramTypingRepository.save(enneagramTyping);			//saveAndFlush if not saved
		
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
		assertThat(foundEnneagramTyping.get()).isNotNull();
        assertThat(foundEnneagramTyping.get().getEntry().getName()).isEqualTo(entry.getName());
        assertThat(foundEnneagramTyping.get().getTypist().getName()).isEqualTo(typist.getName());				
    }
	
	
	@DisplayName("JUnit test for returning nothing when finding enneagram typing by typist and entry name")
    @Test
    public void givenEmptyEnneagramTyping_whenFindEnneagramTypingByTypistAndEntryName_thenReturnNothing(){

        //given
    	
        //when  
		//save nothing

		Optional<EnneagramTyping> foundEnneagramTyping = 
						enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName("This typist does not exist", "Some character that does not exist");

        // then
		assertThat(foundEnneagramTyping.isEmpty());				
    }
	
	
	
	
	@DisplayName("JUnit test for deleting enneagram typing by id")
    @Test
    public void givenEnneagramTyping_whenDeleteById_thenReturnNull(){

        //given
		entryRepository.save(entry);
		typistRepository.save(typist);
		enneagramTypingRepository.save(enneagramTyping);
    	
        //when 
		enneagramTypingRepository.deleteById(enneagramTyping.getId());
		
        // then
		assertThat(enneagramTypingRepository.findById(enneagramTyping.getId())).isEmpty();
    }
}
