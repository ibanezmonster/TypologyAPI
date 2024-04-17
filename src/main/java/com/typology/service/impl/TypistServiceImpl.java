package com.typology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.typology.entity.entry.Typing;
import com.typology.entity.user.Typist;
import com.typology.repository.TypistRepository;
import com.typology.service.TypistService;

@Service
public class TypistServiceImpl implements TypistService
{
	@Autowired
	TypistRepository typistRepository;
	
	public Typist saveTypist(Typist typist){
		Typist savedTypist = typistRepository.save(typist);
		return savedTypist;
	}
}
