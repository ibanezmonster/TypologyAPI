package com.typology.entity.entry;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Embeddable
@Entity
@Data
public class TypingID
{	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY) //bidirectional
	@JoinColumn(name="entry_id")
	private Entry entry;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY) //unidirectional  
	@JoinColumn(name="typist_id")
	private Typist typist;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER) //unidirectional	//crashes here if LAZY //experiment with different proxy class settings to enable lazy loading
	@JoinColumn(name="typology_system_id")
	private TypologySystem typologySystem;
}
