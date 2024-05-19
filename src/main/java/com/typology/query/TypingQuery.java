package com.typology.query;

public class TypingQuery
{
	public static final String FIND_USER_ENNEAGRAM_TYPING_BY_NAME = 
	"""			
		select x		
		from EnneagramTyping x
		join fetch entry e
		join fetch typist y
		where y.name = :yname
		and e.name = :ename
 	""";
	
	
	public static final String FIND_USER_TYPING_BY_ENTRY_AND_SYSTEM = 
	"""			
		select x		
		from Typing x
		join fetch entry e
		join fetch typist y
		join fetch typologySystem z
		where y.name = :yname
		and e.name = :ename
		and z.name = :zname
 	""";
	
//doesn't work	
//	
//	public static final String DELETE_USER_TYPING_BY_ENTRY_AND_SYSTEM = 
//	"""			
//		delete		
//		from Typing 
//		join entry e
//		join typist y
//		join typologySystem z
//		where y.id.name = :yname
//		and e.id.name = :ename
//		and z.id.name = :zname
// 	""";
//	
	
	public static final String FIND_ALL_OF_USER_TYPINGS = 
	"""			
		select x		
		from Typing x
		join fetch typist y
		join fetch entry e
		where y.name = :yname
 	""";
}
