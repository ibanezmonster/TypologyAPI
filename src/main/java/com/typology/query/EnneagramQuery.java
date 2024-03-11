package com.typology.query;

public final class EnneagramQuery
{
//	public static final String GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS = 
//	"""			
//		select * 
//		from entry 
//		where enneagram_core_type = :type
// 	""";
			
	public static final String GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS = 
	"""			
		select e
		from Entry e		
		where e.enneagramCoreType = :type
 	""";
}
