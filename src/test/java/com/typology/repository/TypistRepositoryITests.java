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
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class TypistRepositoryITests extends ContainerStartup
{
	@Autowired
	private TypistRepository typistRepository;
	private Typist typist;

	@BeforeEach
    public void setup(){
		typist = new Typist();		
		typist.setName("UFDISUFODS");
	}
	
	
	@DisplayName("JUnit test for saving typist operation")
    @Test
    public void givenTypist_whenSave_thenReturnSavedTypist(){

        //given
    	
        //when  	
		Typist savedTypist = typistRepository.save(typist);

        // then
        assertThat(savedTypist).isNotNull();
        assertThat(savedTypist.getId()).isGreaterThan(0);
    }
	
	
	@DisplayName("JUnit test for saving typist operation and finding by name")
    @Test
    public void givenTypist_whenSave_thenReturnSavedTypistByName(){

        //given
		typistRepository.save(typist);
		
        //when  	
		Optional<Typist> foundTypist = typistRepository.findByName(typist.getName());

        // then
        assertThat(foundTypist).isNotNull();
        assertThat(foundTypist.get().getName()).isEqualTo(typist.getName());
    }
	
	
	@DisplayName("JUnit test for returning nothing when finding by nonexistent typist by name")
    @Test
    public void givenNonexistentTypist_whenFindByName_thenReturnNothing(){

        //given
		
        //when  	
		Optional<Typist> foundTypist = typistRepository.findByName(typist.getName());

        // then
        assertThat(foundTypist).isEmpty();
    }
}
