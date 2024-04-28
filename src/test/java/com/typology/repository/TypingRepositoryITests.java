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
	private TypologySystem typologySystem;

	@BeforeEach
    public void setup(){
		
		typist = new Typist();
		typist.setId(110);
		typist.setName("UFDISUFODS");
		
		entry = new Entry();		
		entry.setId(789);
		entry.setName("Some character");					
		
		typologySystem = new TypologySystem();
		typologySystem.setId(50);
		typologySystem.setName("Mystery System");
		
		typing = Typing.builder()
					   .typist(typist)
					   .entry(entry)
					   .typologySystem(typologySystem)
					   .build();
	}
	
	
	@DisplayName("JUnit test for saving typing operation")
    @Test
    public void givenTyping_whenSave_thenReturnSavedTyping(){

        //given
		
    	
        //when
		typistRepository.save(typist);
		entryRepository.save(entry);
		typologySystemRepository.save(typologySystem);
		
		Typing savedTyping = typingRepository.save(typing);

        // then
		//assertThat(1).isEqualTo(1);
        assertThat(savedTyping).isNotNull();
        assertThat(savedTyping.getId()).isGreaterThan(0);
    }
	
	
//	@DisplayName("JUnit test for saving typist operation and finding by name")
//    @Test
//    public void givenTypist_whenSave_thenReturnSavedTypistByName(){
//
//        //given
//		typistRepository.save(typist);
//		
//        //when  	
//		Optional<Typist> foundTypist = typistRepository.findByName(typist.getName());
//
//        // then
//        assertThat(foundTypist).isNotNull();
//        assertThat(foundTypist.get().getName()).isEqualTo(typist.getName());
//    }
}
