package com.typology.entity.user;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.typology.security.ValidPassword;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "app_user")
public class AppUser
{
//	public AppUser(long id, String name, String pwd, String role, ZonedDateTime registrationTimestamp, String status){
//		this.id = id;
//		this.name = name;
//		this.pwd = pwd;
//		this.role = role;
//		this.registrationTimestamp = registrationTimestamp;
//		this.status = status;
//	}
	
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private long id;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	private String pwd;
	private String role;
	
	//@JsonIgnore
    @OneToMany(mappedBy="appUser",fetch=FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
	
	private ZonedDateTime registrationTimestamp;
	private String status;
}
