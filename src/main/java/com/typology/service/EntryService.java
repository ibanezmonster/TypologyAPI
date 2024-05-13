package com.typology.service;


import java.util.Optional;

import com.typology.entity.entry.Entry;




public interface EntryService
{
	//basic queries

	Optional<Entry> getEntry(String name);
	Entry saveEntry(Entry entry);
	//List<Entry> getAllEntries();
	//void deleteEntry(long id);
	
	//specialized queries
	//List<Entry> findAllOfEnneagramCoreType();
	//List<Entry> findAllOfEnneagramCoreType(int type);
}
