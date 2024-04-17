package com.typology.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//public record LoginDTO(@NotBlank String name, 
//					   @NotBlank String pwd)
//{}



@Data
public class LoginDTO
{
	private @NotBlank String name; 
	private @NotBlank String pwd;
}