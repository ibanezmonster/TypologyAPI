package com.typology.entity.typologySystem;

import org.checkerframework.common.value.qual.IntRange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typology.annotation.ValidOrderedTritypeValues;
import com.typology.entity.entry.Entry;
import com.typology.entity.user.Typist;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//import javax.validation.ConstraintViolationException;



@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="Enneagram_Typing")
public final class EnneagramTyping extends TypologySystemTyping
//public class EnneagramTyping 
{
	protected static final int[] val = {1,2,3};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	//unique
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="entry_id")
	private Entry entry;
	
	//unique
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="typist_id")
	private Typist typist;
	
	//required fields------------------------------------------------------------------
	@NotNull
	@Min(value = 1, message = "Core type must be between 1 and 9")
	@Max(value = 9, message = "Core type must be between 1 and 9")	
	private int coreType;
	
	@NotNull
	@Min(value = 1, message = "Core type must be between 1 and 9")
	@Max(value = 9, message = "Core type must be between 1 and 9")	
	private int wing;
	
	
	//optional fields------------------------------------------------------------------
	@Column(nullable=true)
	@ValidOrderedTritypeValues(message = "Tritype value is invalid")
	private int tritypeOrdered;
		
	//@Size(max=3)
	private int overlay;
	

	//valid input: sp, sx, or so
	private String instinctMain;
	
	//valid input: 
	private String instinctStack;
	
	private String exInstinctMain;
	private String exInstinctStack;
	
	//purely calculated fields------------------------------------------------------------------
	@JsonIgnore
	private int tritypeUnordered;
	
	@JsonIgnore
	private String instinctStackFlow;
	
	@JsonIgnore
	private int exInstinctStackAbbreviation;
	
	@JsonIgnore
	private String exInstinctStackFlow;
}