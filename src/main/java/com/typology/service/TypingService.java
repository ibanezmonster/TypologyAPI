package com.typology.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.TypologySystemTyping;

import io.netty.handler.codec.http.HttpResponseStatus;

public interface TypingService
{
	ResponseEntity<?> viewAllOfMyTypings() throws JsonProcessingException;			
	ResponseEntity<?> viewTyping(String entryName, String typologySystem) throws JsonProcessingException;
	ResponseEntity<String> addTyping(String entryName, String typologySystem, TypologySystemTyping typing);
	ResponseEntity<String> updateTyping(String entryName, String typologySystem, TypologySystemTyping typing);
	ResponseEntity<HttpStatus> deleteTyping(String entryName, String typologySystem);
}
