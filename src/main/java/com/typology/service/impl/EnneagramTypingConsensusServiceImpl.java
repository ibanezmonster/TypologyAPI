package com.typology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.entity.user.Typist;
import com.typology.repository.EnneagramTypingConsensusRepository;
import com.typology.service.EnneagramTypingConsensusService;

@Service
public class EnneagramTypingConsensusServiceImpl implements EnneagramTypingConsensusService
{
	@Autowired
	EnneagramTypingConsensusRepository enneagramTypingConsensusRepository;
	
	@Override
	public EnneagramTypingConsensus saveEnneagramTypingConsensus(EnneagramTypingConsensus enneagramTypingConsensus) {
		EnneagramTypingConsensus savedEnneagramTypingConsensus = 
						enneagramTypingConsensusRepository.save(enneagramTypingConsensus);
		
		return savedEnneagramTypingConsensus;
	}
}
