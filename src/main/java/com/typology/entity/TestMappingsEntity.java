package com.typology.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.typology.entity.entry.Category;
import com.typology.entity.entry.Entry;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//@NoArgsConstructor
@Entity
@Table(name = "Test_Mappings")
public class TestMappingsEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private String name;	
}