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

import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;
import com.typology.integration.ContainerStartup;

@DataJpaTest
@AutoConfigureMockMvc(addFilters = false)	//disabling security  
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)	//prevent Spring from trying to use embedded db
@Testcontainers
public class TypologySystemRepositoryITests extends ContainerStartup
{
	@Autowired
	private TypologySystemRepository typologySystemRepository;
	private TypologySystem typologySystem;

	@BeforeEach
    public void setup(){
		typologySystem = new TypologySystem();
		typologySystem.setId(50);
		typologySystem.setName("Mystery System");
	}
	
	
	@DisplayName("JUnit test for saving typology system operation")
    @Test
    public void givenTypologySystem_whenSave_thenReturnSavedTypologySystem(){

        //given
    	
        //when  	
		TypologySystem savedTypologySystem = typologySystemRepository.save(typologySystem);

        // then
        assertThat(savedTypologySystem).isNotNull();
        assertThat(savedTypologySystem.getId()).isGreaterThan(0);
    }
	
	
	@DisplayName("JUnit test for saving typology system operation and finding by name")
    @Test
    public void givenTypologySystem_whenSave_thenFindSavedTypologySystemByName(){

        //given
		typologySystemRepository.save(typologySystem);
    	
        //when  	
		Optional<TypologySystem> foundTypologySystem = typologySystemRepository.findByName(typologySystem.getName());

        // then
        assertThat(foundTypologySystem).isNotNull();
        assertThat(foundTypologySystem.get().getName()).isEqualTo(typologySystem.getName());
    }
	
	
	
	
	
	@DisplayName("JUnit test for returning nothing when finding by nonexistent typology system by name")
    @Test
    public void givenNonexistentTypologySystem_whenFindByName_thenReturnNothing(){

        //given
		typologySystemRepository.save(typologySystem);
    	
        //when  	
		Optional<TypologySystem> foundTypologySystem = typologySystemRepository.findByName("This system does not exist");

        // then
        assertThat(foundTypologySystem).isEmpty();
    }
}
