package com.typology.entity.typologySystem;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract sealed class TypologySystemTyping permits EnneagramTyping, EnneagramTypingConsensus, AttitudinalPsycheTyping
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Override
	public String toString() {
		return String.format("Typed Profile for: [%s]", id);
	}
}
