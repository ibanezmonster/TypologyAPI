package com.typology.query;

public final class EntryQuery
{
	public static final String FIND_ENTRY_BY_NAME = 
	"""			
		select e
		from Entry e	
		where e.name = :names
 	""";
}