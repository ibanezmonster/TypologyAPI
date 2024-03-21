package com.typology.entity.entry;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;

import static com.typology.query.EntryQuery.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;
import com.typology.query.EnneagramQuery;
import com.typology.query.EntryQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SecondaryTable;
import lombok.Data;

@Data
@Entity
@Table(name = "entry")
//@NamedQueries(value = {
//				@NamedQuery(name = "find_by_name", query = FIND_ENTRY_BY_NAME)
				//@NamedQuery(name = "query_jpql", query = EnneagramQuery.GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS)
//				})
//@SecondaryTable(name = "typing", pkJoinColumns = @PrimaryKeyJoinColumn(name = "typist"))
public class Entry
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="enneagram_typing_consensus_id", referencedColumnName = "id")
	private EnneagramTypingConsensus enneagramTypingConsensus;

	
	@OneToMany(mappedBy="entry", fetch = FetchType.LAZY)
	private List<Typing> typings = new ArrayList<>();
}