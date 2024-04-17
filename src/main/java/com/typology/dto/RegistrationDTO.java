package com.typology.dto;

import com.typology.security.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class RegistrationDTO
{
	private @NotBlank String name; 
	private @NotBlank @ValidPassword String pwd;
}
