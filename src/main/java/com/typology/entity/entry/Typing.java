package com.typology.entity.entry;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Typing
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
}
