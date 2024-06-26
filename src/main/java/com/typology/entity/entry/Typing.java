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
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@JsonIgnoreProperties({"createdTimestamp", "updatedTimestamp"})
@IdClass(TypingID.class)
@Entity
public class Typing
{	
	@Id
	private Entry entry;
	
	@Id
	private Typist typist;
	
	@Id
	private TypologySystem typologySystem;

	@CreationTimestamp					
	private LocalDateTime createdTimestamp;
		
	@UpdateTimestamp					
	private LocalDateTime updatedTimestamp;
}
