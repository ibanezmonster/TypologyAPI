package com.typology.entity.typologySystem;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typology.entity.entry.Entry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="enneagram_typing_consensus")
public final class EnneagramTypingConsensus extends TypologySystemTyping
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	private int coreType;
	private int wing;
	private int tritypeOrdered;
	private int tritypeUnordered;
	private int overlay;
	
	
	//traditional instincts
	private String instinctMain;
	private String instinctStack;
	
	//@Column(insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	private String instinctStackFlow;
	
	
	//expanded instincts
	private String exInstinctMain;
	private String exInstinctStack;
	
	//@Column(insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	private String exInstinctStackAbbreviation;
	
	//@Column(insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
	private String exInstinctStackFlow;
	
	//@OneToOne(fetch=FetchType.LAZY, mappedBy="consensusEnneagramTyping")
	//private Entry entry;
}
