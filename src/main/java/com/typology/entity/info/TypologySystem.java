package com.typology.entity.info;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TypologySystem
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
}
