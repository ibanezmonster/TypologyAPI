package com.typology.query;

public class TypistQuery
{
	public static final String FIND_TYPIST_BY_NAME = 
	"""			
		select t		
		from Typist t
		where t.name = :name
 	""";
}
