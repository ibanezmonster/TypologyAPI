package com.typology.entity.entry;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@JsonIgnoreProperties({"entry", "createdTimestamp", "updatedTimestamp"})
@Entity
public class Typing
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY) //bidirectional
	@JoinColumn(name="entry_id")
	private Entry entry;
	
	@ManyToOne(fetch=FetchType.EAGER) //unidirectional  //crashes here if LAZY
	@JoinColumn(name="typist_id")
	private Typist typist;
	
	@ManyToOne(fetch=FetchType.EAGER) //unidirectional	//crashes here if LAZY
	@JoinColumn(name="typology_system_id")
	private TypologySystem typologySystem;

	@CreationTimestamp					
	private LocalDateTime createdTimestamp;
		
	@UpdateTimestamp					
	private LocalDateTime updatedTimestamp;
}
