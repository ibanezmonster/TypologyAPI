package com.typology.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.Typist;
import com.typology.exception.ExMessageBody;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;
import com.typology.repository.TypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.repository.TypologySystemRepository;
import com.typology.service.RestTemplateClass;
import com.typology.service.TypingService;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
@Transactional
public class TypingServiceImpl implements TypingService
{
	@Autowired
	TypingRepository typingRepository;
	
	@Autowired
	EnneagramTypingRepository enneagramTypingRepository;
	
	@Autowired
	EntryRepository entryRepository;
	
	@Autowired
	TypistRepository typistRepository;
	
	@Autowired
	TypologySystemRepository typologySystemRepository;
	
	@Autowired
	private RestTemplateClass restTemplate;
	
	
	
	public List<Typing> viewAllOfMyTypings(){		
		List<Typing> typings = new ArrayList<>();
		
		try {
			typings = typingRepository.viewAllOfMyTypings("Rob Zeke")
					  				  .orElseThrow(ResourceNotFoundException::new);
		}
		
		catch(ResourceNotFoundException ex) {
			
		}
		
		
		return typings;
	}
	
	
	public TypologySystemTyping viewTyping(String entryName, String typologySystem) {
		
//		ResponseEntity<List<Typing>> response;
//		ResponseEntity<Object[]> responseEntity =
//				   restTemplate.getForEntity("http://localhost:8080/api/v1/profile/test123/my_typing", Object[].class);
//		
//		Object[] objects = responseEntity.getBody();
//		ObjectMapper mapper = new ObjectMapper();
//
//		return Arrays.stream(objects)
//					  .map(object -> mapper.convertValue(object, Typing.class))
//					  .map(Typing::getTypist)
//					  .collect(Collectors.toList());
//		ResponseEntity<List<Typing>> response = null;
		
		
		
//		ResponseEntity<List<Typing>> response = restTemplate.exchange("http://localhost:8080/api/v1/profile/test123/my_typing",
//														  				HttpMethod.GET,
//														  				null,
//														  				new ParameterizedTypeReference<List<Typing>>() {}
//														  				);

		//List<Typing> typing = typingRespository

		
		//get typist name from user session
		String typistName = "Rob Zeke";
		
		//run the query based on the system being used
		//for this example, use enneagram
		
		TypologySystemTyping list = null;
		
		switch(typologySystem) {
		case "enneagram":
			EnneagramTyping typing = enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typistName, entryName)
									  								 .orElseThrow(ResourceNotFoundException::new);
			list = typing;
		}
		
		
		
		return list;
		
				
				
//		try{
//				List<Typing> typing = typingRepository.findTypingsByUserAndEntryName("OPS", entryName)
//													  .orElseThrow(ResourceNotFoundException::new);
//				response = ResponseEntity.of(typing);
//				response = ResponseEntity.status(HttpStatus.CREATED)
//										 .body("For entry: " + entryName + ", " + " these are your typings:");
//		}
//		
//		catch(ResourceNotFoundException e){
//			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
//	 				 				 .body("Typing entry could not be created. Profile for: " + entryName + " not found.");
//		}
//				
//		catch(Exception e){
//			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
//	 				 				 .body("Your typing for: " + entryName + " could not be found.");
//		}
//
//		return response;
	}
	
	
	
	
	public ResponseEntity<String> addTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping) {
		
		ResponseEntity<String> response;
		
		
		try{			
			Typist typist = this.typistRepository.findByName("OPS")
					 							 .orElseThrow();	//NoSuchElementException

			//transaction: save in enneagram_typing table
			switch(typologySystem) {
				case "enneagram":
					createEnneagramTyping(entryName, typist, enneagramTyping);
			}
			
			
			//save in typing table
			Typing typing = new Typing();
			Entry entry = this.entryRepository.findByName(entryName)
											  .orElseThrow();	//NoSuchElementException
						
			TypologySystem typologySystemEntity = this.typologySystemRepository.findByName(typologySystem)
																				.orElseThrow();	//NoSuchElementException
			
			typing.setEntry(entry);
			typing.setTypist(typist);
			typing.setTypologySystem(typologySystemEntity);
			typing.setCreatedTimestamp(LocalDateTime.now());
			
			typingRepository.save(typing);
			
		
			response = ResponseEntity.status(HttpStatus.CREATED)
					 				 .body("For entry: " + entryName + ", " + " new typing is created");
		}
		
		catch(ResourceNotFoundException e){
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 				 				 .body("Typing entry could not be created. Profile for: " + entryName + " not found.");
		}
		
		catch(DataIntegrityViolationException e){
			response = ResponseEntity.status(HttpStatus.CONFLICT)
	 				 				 .body(ExMessageBody.MSG_PREFIX + "Profile for: " + entryName + " already exists.");
		}
		
		catch(NoSuchElementException e){
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 				 				 .body("Entry, typology system, or typist not found. Typing entry could not be created.");
		}
		
		catch(Exception e){
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 				 				 .body("Typing entry could not be created.");
		}
		
		return response;
	}
	
	
	
	public ResponseEntity<String> updateTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping) {
		
		ResponseEntity<String> response;
		
		//create switch based on type of object (typology system used)
		//transaction: save in enneagramtyping table and typing table
		try{
			
			Typist typist = this.typistRepository.findByName("OPS")
					 							 .orElseThrow();	//NoSuchElementException
			
			switch(typologySystem) {
				case "enneagram":
					updateEnneagramTyping(entryName, typist, enneagramTyping);
			}
			
			response = ResponseEntity.status(HttpStatus.OK)
					 				 .body("For entry: " + entryName + ", " + " typing is updated");
		}
		
		catch(ResourceNotFoundException e){
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 				 				 .body(ExMessageBody.MSG_PREFIX + "Profile for: " + entryName + " not found.");
		}
		
		catch(Exception e){
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 				 				 .body(ExMessageBody.MSG_PREFIX + "Typing entry could not be updated.");
		}
		
		return response;
	}
	
	
	public ResponseEntity<String> deleteTyping(String entryName, String typologySystem) {
		
		//create switch based on type of object (typology system used)
		//transaction: save in enneagramtyping table and typing table
		try{
			
			Typist typist = this.typistRepository.findByName("OPS")
					 							 .orElseThrow();	//NoSuchElementException
			
			if(!enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entryName)
				 						 .isPresent()) 
			{
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
		 				 			  .body(ExMessageBody.MSG_PREFIX + "Profile for: " + entryName + " not found."); 
			}
			
			else{				
				return  ResponseEntity.status(HttpStatus.NO_CONTENT)
			 			  			  .body("Your typing for: " + entryName + " is deleted.");
			}
		}
				
		catch(Exception e){
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		 			  			  .body(ExMessageBody.MSG_PREFIX + "Exception in deleting typing.");				
		}
	}


	//void deleteTyping() {}
//	private boolean isDeleted;					//implement soft delete to track whether something is deleted or not, there's a column for this also
//	
//	@PreRemove									//"hook" (jpa life cycle method) that fires when a row is deleted- used to set the data member isDeleted to true
//	private void preRemove(){
//		LOGGER.info("Setting isDeleted to True");
//		this.isDeleted = true;
//	}
	
	
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
		
		System.out.println(enneagramTyping.getEntry().getId());
		System.out.println(enneagramTyping.getTypist().getId());
				
		//add enneagram typing table record
		enneagramTypingRepository.save(enneagramTyping);
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
}
