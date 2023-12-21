package com.typology.service;

import java.util.List;
import java.util.Optional;

import com.typology.entity.*;
import com.typology.entity.entry.Entry;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EntryService
{
	//basic queries
	List<Entry> getAllEntries();
	Entry createEntry(Entry entry);
    Entry updateEntry(Entry entry); 
	Entry getEntry(long id);
	void deleteEntry(long id);
	
	//specialized queries
	//List<Entry> findAllOfEnneagramCoreType();
	List<Entry> findAllOfEnneagramCoreType(int type);
	List<Entry> findAllOfEnneagramCoreType2();
}
