package com.typology.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.repository.EntryRepository;
import com.typology.service.impl.EntryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EntryServiceImplTests
{
	@Mock
	private EntryRepository entryRepository;
	
	@InjectMocks
	private EntryServiceImpl entryService;
	
	private Entry entry;

	
	@BeforeEach
	public void setup(){
		entryRepository = Mockito.mock(EntryRepository.class);
		entryService = new EntryServiceImpl(entryRepository);
			
		entry = new Entry();
		entry.setName("Haruhi Suzumiya");
		entry.setCategory(Category.ANIME);
	}
	
	
	
	@DisplayName("JUnit service test for save entry")
	@Test
	public void givenEntryObject_whenSave_thenReturnSavedEntry(){
		
	    // given
	    given(entryRepository.save(entry))
	    					 .willReturn(entry);
	    
	    // when                
	    Entry savedEntry = entryService.saveEntry(entry);
	            
	    // then
	    assertThat(savedEntry).isNotNull();
	    assertThat(savedEntry.getName()).isEqualTo(entry.getName());    	
	}
	
	
	
	@DisplayName("JUnit service test for save entry (negative scenario)")
	@Test
	public void givenNullEntryObject_whenSave_thenReturnNothing(){
		
	    // given
	    given(entryRepository.save(entry))
	    					 .willReturn(null);
	    
	    // when                
	    Entry savedEntry = entryService.saveEntry(entry);
	            
	    // then
	    assertThat(savedEntry).isNull();	
	}
	
	
	
	@DisplayName("JUnit service test for get entry by name")
	@Test
	public void givenEntryObject_whenSave_thenGetEntryByName(){
		
	    // given
	    given(entryRepository.findByName(entry.getName()))
	    					 .willReturn(Optional.of(entry));
	    
	    // when                
	    Optional<Entry> retrievedEntry = entryService.getEntry(entry.getName());
	            
	    // then
	    assertThat(retrievedEntry).isNotNull();
	    assertThat(retrievedEntry.get().getName()).isEqualTo(entry.getName());    	
	}
	
	
	
	
	@DisplayName("JUnit service test for find entry by name (negative scenario)")
	@Test
	public void givenNullEntryObject_whenFindByName_thenReturnNothing(){
		
	    // given
		given(entryRepository.findByName(entry.getName()))
		 					 .willReturn(Optional.empty());
	    
	    // when                
	    Optional<Entry> savedEntry = entryService.getEntry(entry.getName());
	            
	    // then
	    assertThat(savedEntry.isEmpty());	
	}
}
