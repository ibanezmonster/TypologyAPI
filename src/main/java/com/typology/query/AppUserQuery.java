package com.typology.query;

public class AppUserQuery
{
	public static final String GET_USER_BY_USERNAME = 
	"""			
		select e
		from AppUser e		
		where e.name = :username
 	""";
}
