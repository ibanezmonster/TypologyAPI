package com.typology.entity.user;


import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private int id;
	
	@Column(unique = true)
	private String name;
	private String pwd;
	private String role;
	private ZonedDateTime registrationTimestamp;
	private String status;
}
