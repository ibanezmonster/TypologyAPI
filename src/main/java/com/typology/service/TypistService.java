package com.typology.service;

import org.springframework.http.ResponseEntity;

import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.Typist;

public interface TypistService
{
	Typist saveTypist(Typist typist);
}
