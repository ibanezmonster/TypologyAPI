package com.typology.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

//public record LoginDTO(@NotBlank String name, 
//					   @NotBlank String pwd)
//{}



@Data
@Builder
public class LoginDTO
{
	private @NotBlank String name; 
	private @NotBlank String pwd;
}