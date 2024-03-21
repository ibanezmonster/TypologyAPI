package com.typology.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;

public interface TypingService
{
	List<Typing> viewTyping(String entryName, String typologySystem);
	ResponseEntity<String> addTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping);
	ResponseEntity<String> updateTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping);
	//void deleteTyping();
}
