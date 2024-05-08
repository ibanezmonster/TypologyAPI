package com.typology.dto;

import com.typology.security.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class RegistrationDTO
{
	private @NotBlank String name; 
	private @NotBlank @ValidPassword String pwd;
}
