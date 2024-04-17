package com.typology.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystemTyping;

import io.netty.handler.codec.http.HttpResponseStatus;

public interface TypingService
{
	List<Typing> viewAllOfMyTypings();			
	TypologySystemTyping viewTyping(String entryName, String typologySystem);
	ResponseEntity<String> addTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping);
	ResponseEntity<String> updateTyping(String entryName, String typologySystem, EnneagramTyping enneagramTyping);
	ResponseEntity<HttpStatus> deleteTyping(String entryName, String typologySystem);
}
