package com.typology.utils;

public class EnumStringComparison
{
	public static <T extends Enum<T>> boolean isStringInEnum(String input, Class<T> enumClass) {
		for(T enumValue : enumClass.getEnumConstants()) {
			if(enumValue.name().equals(input)) 
				return true;
		}
		
		return false;
	}
}
