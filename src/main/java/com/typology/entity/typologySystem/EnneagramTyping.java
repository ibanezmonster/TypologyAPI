package com.typology.entity.typologySystem;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class EnneagramTyping extends TypologySystem
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	int coreType;
	int wing;
	int tritypeOrdered;
	int tritypeUnOrdered;
	int overlay;
	
	
	//traditional instincts
	String instinctMain;
	String instinctStack;
	
	@JsonIgnore
	String instinctFlow;
	
	
	//expanded instincts
	String exInstinctMain;
	String exInstinctStack;
	
	@JsonIgnore
	String exInstinctStackAbbreviation;
	
	@JsonIgnore
	String exInstinctFlow;
}
