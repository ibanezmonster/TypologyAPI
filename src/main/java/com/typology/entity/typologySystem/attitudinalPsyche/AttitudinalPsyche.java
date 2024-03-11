package com.typology.entity.typologySystem.attitudinalPsyche;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AttitudinalPsyche
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
}
