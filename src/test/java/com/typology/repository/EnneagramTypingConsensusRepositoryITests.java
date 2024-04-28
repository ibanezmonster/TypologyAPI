package com.typology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class EnneagramTypingConsensusRepositoryITests extends ContainerStartup
{
	@Autowired
	private EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	private EnneagramTypingConsensus enneagramTypingConsensus;
	
	@BeforeEach
    public void setup(){
		enneagramTypingConsensus = new EnneagramTypingConsensus();
		enneagramTypingConsensus.setCoreType(7);
		enneagramTypingConsensus.setWing(8);
    	
    }
	
	@DisplayName("JUnit test for saving enneagram typing consensus operation")
    @Test
    public void givenEnneagramTypingConsensus_whenSave_thenReturnSavedEnneagramTypingConsensus(){

        //given
    	
        //when  	
		EnneagramTypingConsensus savedEnneagramTypingConsensus = enneagramTypingConsensusRepository.save(enneagramTypingConsensus);

        // then
        assertThat(savedEnneagramTypingConsensus).isNotNull();
        assertThat(savedEnneagramTypingConsensus.getId()).isGreaterThan(0);
    }
	    
	
	
}