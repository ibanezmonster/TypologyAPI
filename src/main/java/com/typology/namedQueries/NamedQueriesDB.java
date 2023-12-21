package com.typology.namedQueries;

public final class NamedQueriesDB
{
	public static final String GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS = 
	"""			
		select * 
		from entry 
		where enneagramCoreType = :type
 	""";
			
	public static final String GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS_JPQL = 
	"""			
		select e 
		from Entry e 
 	""";
}
