package com.typology.dto;

import com.typology.security.AppUserRoles;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppUserRoleDTO
{
	@NotNull
	String role; 
}
