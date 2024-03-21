package com.typology.query;

public class TypologySystemQuery
{
	public static final String FIND_TYPOLOGY_SYSTEM_BY_NAME = 
	"""			
		select t		
		from TypologySystem t
		where t.name = :name
 	""";
}
