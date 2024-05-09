package com.typology.service.typingCRUD;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.Typist;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class EnneagramTypingCRUD
{
	@Autowired
	EnneagramTypingRepository enneagramTypingRepository;
	
	@Autowired
	EntryRepository entryRepository;

	//void deleteTyping() {}
//	private boolean isDeleted;					//implement soft delete to track whether something is deleted or not, there's a column for this also
//	
//	@PreRemove									//"hook" (jpa life cycle method) that fires when a row is deleted- used to set the data member isDeleted to true
//	private void preRemove(){
//		LOGGER.info("Setting isDeleted to True");
//		this.isDeleted = true;
//	}
	
	public EnneagramTypingCRUD(EnneagramTypingRepository enneagramTypingRepository) {
		this.enneagramTypingRepository = enneagramTypingRepository;
	}
	
	public EnneagramTypingCRUD(EnneagramTypingRepository enneagramTypingRepository, EntryRepository entryRepository) {
		this.enneagramTypingRepository = enneagramTypingRepository;
		this.entryRepository = entryRepository;
	}
	
	
	public void createEnneagramTyping(String entryName, Typist typist, EnneagramTyping enneagramTyping) throws DataIntegrityViolationException{
		
		//handle scenario of record already exists
		if(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entryName)
								 	.isPresent()) 
		{
			throw new DataIntegrityViolationException("Typing already exists.");
		}
		
		//get entryId for fk
		Entry entry = this.entryRepository.findByName(entryName)
										  .orElseThrow(ResourceNotFoundException::new);			
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);
				
		//add enneagram typing table record
		enneagramTypingRepository.save(enneagramTyping);
	}
	
	
	
	
	public Optional<EnneagramTyping> readEnneagramTyping(String typistName, String entryName) throws DataIntegrityViolationException{
		return enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typistName, entryName);
	}	
	
	
	
	
	
	public void updateEnneagramTyping(String entryName, Typist typist, EnneagramTyping enneagramTyping) throws Exception{

		//get existing enneagram typing in the db for fk
		EnneagramTyping enneagramTypingToUpdate = enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entryName)
												  						   .orElseThrow(ResourceNotFoundException::new);	

		enneagramTypingToUpdate.setId(enneagramTypingToUpdate.getId());
		//enneagramTypingToUpdate.setTypistId(enneagramTypingToUpdate.getTypistId());
		
		enneagramTypingToUpdate.setCoreType(enneagramTyping.getCoreType());
		enneagramTypingToUpdate.setWing(enneagramTyping.getWing());
		enneagramTypingToUpdate.setTritypeOrdered(enneagramTyping.getTritypeOrdered());
		enneagramTypingToUpdate.setOverlay(enneagramTyping.getOverlay());
		enneagramTypingToUpdate.setInstinctMain(enneagramTyping.getInstinctMain());
		enneagramTypingToUpdate.setInstinctStack(enneagramTyping.getInstinctStack());
		enneagramTypingToUpdate.setExInstinctMain(enneagramTyping.getExInstinctMain());
		enneagramTypingToUpdate.setExInstinctStack(enneagramTyping.getExInstinctStack());
		
		enneagramTypingRepository.save(enneagramTypingToUpdate);	
	}


	
	public void deleteEnneagramTyping(EnneagramTyping enneagramTypingToDelete) throws Exception{

		enneagramTypingRepository.deleteById(enneagramTypingToDelete.getId());	
	}	
	
	
}
