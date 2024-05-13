package com.typology.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.dto.MyTypingsDTO;
import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.typologySystem.TypologySystemTyping;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.Typist;
import com.typology.exception.ExMessageBody;
import com.typology.filter.AuthoritiesLoggingAfterFilter;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;
import com.typology.repository.TypingRepository;
import com.typology.repository.TypistRepository;
import com.typology.repository.TypologySystemRepository;
import com.typology.service.RestTemplateClass;
import com.typology.service.TypingService;
import com.typology.service.typingCRUD.EnneagramTypingCRUD;

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
    ObjectMapper objectMapper;
	
	@Autowired
	EnneagramTypingRepository enneagramTypingRepository;
	
	@Autowired
	EntryRepository entryRepository;
	
	@Autowired
	EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	
	@Autowired
	TypistRepository typistRepository;
	
	@Autowired
	TypologySystemRepository typologySystemRepository;
	
	@Autowired
	private RestTemplateClass restTemplate;
	
	private final Logger LOG = Logger.getLogger(TypingServiceImpl.class.getName());
	


	
	public TypingServiceImpl(TypingRepository typingRepository, EntryRepository entryRepository, EnneagramTypingRepository enneagramTypingRepository, 
								EnneagramTypingConsensusRepository enneagramTypingConsensusRepository, 
								TypistRepository typistRepository, TypologySystemRepository typologySystemRepository)
	{
		this.typingRepository = typingRepository;
		this.entryRepository = entryRepository;
		this.enneagramTypingRepository = enneagramTypingRepository;
		this.enneagramTypingConsensusRepository = enneagramTypingConsensusRepository;
		this.typistRepository = typistRepository;
		this.typologySystemRepository = typologySystemRepository;
	}
	
	
	
	
	
	

	
	public ResponseEntity<String> addTyping(String entryName, String typologySystem, TypologySystemTyping typing) {
		
		ResponseEntity<String> response;
		
		
		try{			
			//get logged-in user
			//and check if they exist in Typist table			
			Typist typist = this.typistRepository.findByName("Newtypist")
					 							 .orElseThrow();	//NoSuchElementException

			//transaction: save in enneagram_typing table
			switch(typologySystem){
				case "enneagram" ->	{
										EnneagramTyping enneagramTyping = (EnneagramTyping) typing;
										EnneagramTypingCRUD enneagramTypingCRUD = new EnneagramTypingCRUD(enneagramTypingRepository, entryRepository);
										enneagramTypingCRUD.createEnneagramTyping(entryName, typist, enneagramTyping);
									}
				
				default -> {return null;}
			};
			
			
			//save in typing table
			Entry entry = this.entryRepository.findByName(entryName)
											  .orElseThrow();	//NoSuchElementException
						
			TypologySystem typologySystemEntity = this.typologySystemRepository.findByName(typologySystem)
																				.orElseThrow();	//NoSuchElementException
			
			Typing newTyping = new Typing();
			newTyping.setEntry(entry);
			newTyping.setTypist(typist);
			newTyping.setTypologySystem(typologySystemEntity);
			newTyping.setCreatedTimestamp(LocalDateTime.now());
			
			typingRepository.save(newTyping);
			
		
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
	
	
	
	
	
	
	
	
	public ResponseEntity<?> viewTyping(String entryName, String typologySystem) throws JsonProcessingException {	
		
		//get typist name from user session
		String typistName = "Newtypist";
		
		//for future typing system additions:
		//run the query based on the system being used
		//for this example, use enneagram
		
		TypologySystemTyping list = null;
		
		try {
			switch(typologySystem) {
			case "enneagram":
				 
				EnneagramTypingCRUD enneagramTypingCRUD = new EnneagramTypingCRUD(enneagramTypingRepository);
				EnneagramTyping enneagramTyping = enneagramTypingCRUD.readEnneagramTyping(typistName, entryName)
																	 .orElseThrow(ResourceNotFoundException::new);  
				list = (TypologySystemTyping) enneagramTyping;		
			}
		}
		
		catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
	 				 			 .body("Typing for: " + entryName + " not found.");			
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 				 		     .body(ExMessageBody.MSG_PREFIX + "An unexpected error occurred.");			
		}
				
		objectMapper = new ObjectMapper();
		
		return list == null ? ResponseEntity.status(HttpStatus.NOT_FOUND)
										    .body("Typing for: " + entryName + " not found.")
							: ResponseEntity.status(HttpStatus.OK)
											.body(objectMapper.writeValueAsString(list));
	}
	
	
	
	
	
	
	public ResponseEntity<?> viewAllOfMyTypings() throws JsonProcessingException {		

		List<Typing> typings = new ArrayList<>();
		List<MyTypingsDTO> myTypingsDTO = new ArrayList<>();		
		
		try {
			typings = typingRepository.viewAllOfMyTypings("Newtypist")
					  				  .orElseThrow(ResourceNotFoundException::new);
			
			//convert to DTO
			for(Typing typing : typings) {
				myTypingsDTO.add(new MyTypingsDTO(typing.getEntry().getName(), 
												  typing.getTypologySystem().getName()));						
			}
			
			objectMapper = new ObjectMapper();
			
			
		}
		
		catch(ResourceNotFoundException e) {
			ResponseEntity.status(HttpStatus.OK)
			 			  .body("No typings found");
		}
		
		return myTypingsDTO.isEmpty() ? ResponseEntity.status(HttpStatus.OK)
												 	  .body("No typings found")
									  : ResponseEntity.status(HttpStatus.OK)
												      .contentType(MediaType.APPLICATION_JSON)
												      .body(objectMapper.writeValueAsString(myTypingsDTO));
	}
	
	
	
	

	
	
	public ResponseEntity<String> updateTyping(String entryName, String typologySystem, TypologySystemTyping typing) {
		
		ResponseEntity<String> response;
		
		//create switch based on type of object (typology system used)
		//transaction: save in enneagramtyping table and typing table
		try {
			
			Typist typist = this.typistRepository.findByName("Newtypist")
					 							 .orElseThrow();	//NoSuchElementException
			
			switch(typologySystem){
			case "enneagram" ->	{
									EnneagramTyping enneagramTyping = (EnneagramTyping) typing;
									EnneagramTypingCRUD enneagramTypingCRUD = new EnneagramTypingCRUD(enneagramTypingRepository);
									enneagramTypingCRUD.updateEnneagramTyping(entryName, typist, enneagramTyping);
								}
			
			default -> {return null;}
			};

			
			response = ResponseEntity.status(HttpStatus.OK)
					 				 .body("For entry: " + entryName + ", " + "typing is updated");
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
	


	


	
	public ResponseEntity<HttpStatus> deleteTyping(String entryName, String typologySystem) {
		
		//create switch based on type of object (typology system used)
		//transaction: save in enneagramtyping table and typing table
		try{
			
			Typist typist = this.typistRepository.findByName("Newtypist")
					 							 .orElseThrow();	//NoSuchElementException

			switch(typologySystem){
			case "enneagram" ->	{								
									EnneagramTypingCRUD enneagramTypingCRUD = new EnneagramTypingCRUD(enneagramTypingRepository);
									EnneagramTyping enneagramTypingToDelete = enneagramTypingCRUD.readEnneagramTyping(typist.getName(), entryName)
																								 .orElseThrow(ResourceNotFoundException::new);
									enneagramTypingCRUD.deleteEnneagramTyping(enneagramTypingToDelete);
								}
			
			default -> {return null;}
			};			
				
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);						
		}
		
		catch(NoSuchElementException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);				
		}
		
		catch(ResourceNotFoundException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);				
		}
				
		catch(Exception e){
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);				
		}
	}	
}
