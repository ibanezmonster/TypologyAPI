package com.typology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.service.EnneagramTypingConsensusService;
import com.typology.service.EnneagramTypingService;


@Service
public class EnneagramTypingServiceImpl implements EnneagramTypingService
{

	@Autowired
	EnneagramTypingRepository enneagramTypingRepository;
	

	public EnneagramTypingServiceImpl(EnneagramTypingRepository enneagramTypingRepository){
		this.enneagramTypingRepository = enneagramTypingRepository;
	}
	
	@Override
	public EnneagramTyping saveEnneagramTyping(EnneagramTyping enneagramTyping)
	{
		EnneagramTyping savedEnneagramTyping = 
				enneagramTypingRepository.save(enneagramTyping);

		return savedEnneagramTyping;
	}
	
}
