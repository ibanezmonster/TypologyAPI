package com.typology.query;

public final class EnneagramQuery
{			
	public static final String GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS = 
	"""			
		select e
		from Entry e		
		where e.enneagramCoreType = :type
 	""";
	
	
	public static final String ADD_ENNEAGRAM_TYPING = 
	"""			
		select e
		from Entry e		
		where e.enneagramCoreType = :type
 	""";
	
	
	public static final String FIND_ENNEAGRAM_TYPING_ID_BY_ENTRY_NAME = 
	"""			
		select t
		from EnneagramTyping t		
		join fetch Entry e
		on t.entryId = e.id
		where e.name = :name
 	""";
}
