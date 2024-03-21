package com.typology.entity.typologySystem;

import org.checkerframework.common.value.qual.IntRange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="enneagramTyping")
public final class EnneagramTyping extends TypologySystemTyping
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	//unique
	@JsonIgnore
	private long entryId;
	
	//unique
	@JsonIgnore
	private long typistId;
	
	//not null check(core_type between 1 and 9),
	//@JsonProperty(required=true)
	//@NotBlank
	//@NotNull
	//@ValidQueryRange
	//@IntRange(from=1, to=9)
	//@Min(value = 1, message = "Core type must be between 1 and 9")
	//@Max(value = 9, message = "Core type must be between 1 and 9")
	private int coreType;
	
	//not null check(core_type between 1 and 9),	
	private int wing;
	
	@JsonIgnore
	private int tritypeOrdered;
	private int tritypeUnordered;
	private int overlay;
	
	
	private String instinctMain;
	private String instinctStack;
	
	@JsonIgnore
	private String instinctStackFlow;
	private String exInstinctMain;
	private String exInstinctStack;
	
	@JsonIgnore
	private String exInstinctStackAbbreviation;
	
	@JsonIgnore
	private String exInstinctStackFlow;
}