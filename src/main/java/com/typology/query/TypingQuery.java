package com.typology.query;

public class TypingQuery
{
	public static final String FIND_USER_ENNEAGRAM_TYPING_BY_NAME = 
	"""			
		select t		
		from Typing t
		join t.entry e
		join t.typist y
		join EnneagramTyping x
		on x.entryId = e.id
		where y.name = :yname
		and e.name = :ename
 	""";
}

//
//select *
//from typing t1_0 
//join entry e1_0 
//on e1_0.id=t1_0.entry_id 
//join typology_system ts1_0 
//on ts1_0.id=t1_0.typology_system_id 
//join enneagram_typing x
//on x.entry_id = e1_0.id
//where e1_0.name='test123' and ts1_0.name='enneagram'







//select t1_0.id,t1_0.created_timestamp,t1_0.entry_id,t1_0.typist_id,t1_0.typology_system_id,t1_0.updated_timestamp 
//from typing t1_0 
//join entry e1_0 on e1_0.id=t1_0.entry_id 
//join typist t2_0 on t2_0.id=t1_0.typist_id 
//join enneagram_typing et1_0 
//on et1_0.entry_id=t1_0.entry_id 
//where t2_0.name='Rob Zeke' and e1_0.name='test123'
