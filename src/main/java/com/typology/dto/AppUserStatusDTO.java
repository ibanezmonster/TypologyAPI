package com.typology.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppUserStatusDTO
{
	@NotNull
	String status; 
}
