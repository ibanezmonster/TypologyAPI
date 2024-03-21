package com.typology.service;


import com.typology.entity.entry.Entry;




public interface EntryService
{
	//basic queries

	Entry getEntry(String name);
	//List<Entry> getAllEntries();
	//void deleteEntry(long id);
	
	//specialized queries
	//List<Entry> findAllOfEnneagramCoreType();
	//List<Entry> findAllOfEnneagramCoreType(int type);
}
