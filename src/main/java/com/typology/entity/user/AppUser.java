package com.typology.entity.user;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class AppUser
{
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)		//throws error if data.sql adds new users that start at 1
	private long id;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	//@Size(min=6, max=15, message="Size must be between 6 and 15 characters")			//@Size is more portable than Hibernate @Length
	private String pwd;
	
	
	//only admin can update a user's role
	//@JsonView(View.Admin.class)
	private String role;
	
	
	//@JsonIgnore
    @OneToMany(mappedBy="appUser",fetch=FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
	
	private ZonedDateTime registrationTimestamp;
	private String status;
}
