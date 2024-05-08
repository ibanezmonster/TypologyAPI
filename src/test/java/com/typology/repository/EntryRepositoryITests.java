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
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class EntryRepositoryITests extends ContainerStartup
{
	@Autowired
	private EntryRepository entryRepository;
	private Entry entry;

	
	@BeforeEach
    public void setup(){
		entry = new Entry();		
		entry.setId(789);
		entry.setName("Some character");
	}
	
	
	@DisplayName("JUnit test for saving entry")
    @Test
    public void givenEntry_whenSave_thenGetEntry(){

        //given
    	
        //when  			
		Entry savedEntry = entryRepository.save(entry);

        // then
		assertThat(savedEntry).isNotNull();
        assertThat(savedEntry.getId()).isGreaterThan(0);
    }
	
	
	@DisplayName("JUnit test for saving and finding entry by name")
    @Test
    public void givenEntry_whenSave_thenFindEntryByName(){

        //given
    	
        //when  			
		entryRepository.save(entry);
		Optional<Entry> foundEntry = entryRepository.findByName(entry.getName());

        // then
		assertThat(foundEntry).isNotNull();
        assertThat(foundEntry.get().getName()).isEqualTo(foundEntry.get().getName());
    }
	
	
	@DisplayName("JUnit test for finding entry by name")
    @Test
    public void givenNonexistentEntry_whenFindEntryByName_thenReturnNothing(){

        //given
    	
        //when  			
		Optional<Entry> foundEntry = entryRepository.findByName("Nonexistent entry");

        // then
		assertThat(foundEntry).isEmpty();
    }
}
