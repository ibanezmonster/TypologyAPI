package com.typology.entity.typologySystem;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract sealed class TypologySystem permits EnneagramTyping
{
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private long id;
}
