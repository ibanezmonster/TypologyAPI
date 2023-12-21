package com.typology.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.typology.entity.entry.Entry;
//import com.typology.mapper.EntryMapper;
import com.typology.repository.EntryRepository;
import com.typology.service.EntryService;

import lombok.AllArgsConstructor;
 
@Service
@Transactional
public class EntryServiceImpl implements EntryService
{
	@Autowired
	EntryRepository entryRepository;
	
	@Override
    public List<Entry> getAllEntries() { 
		return this.entryRepository.findAll();				
    }
		
	@Override
    public Entry getEntry(long id) {
		Optional<Entry> entry = this.entryRepository.findById(id);
		
		return entry.orElseThrow(ResourceNotFoundException::new);	
    }
	
	@Override
	public Entry createEntry(Entry entry) {
		return entryRepository.save(entry);
	}
	
	@Override
    public Entry updateEntry(Entry entry) { 
		Optional<Entry> entryDB = this.entryRepository.findById(entry.getId());				
		
		if(entryDB.isPresent()) {
			Entry entryUpdate = entryDB.get();
			entryUpdate.setEnneagramCoreType(entry.getEnneagramCoreType());
			
			entryRepository.save(entryUpdate);
			
			return entry;
		}
		
		else {
			throw new ResourceNotFoundException("Record not found with id : " + entry.getId());
		}
		
    }
	
	@Override
	public void deleteEntry(long id) {
		Optional<Entry> entrytDb = this.entryRepository.findById(id);
						
		if(entrytDb.isPresent()) {
			this.entryRepository.delete(entrytDb.get());
		}
		
		else {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
		
	}
	
	@Override
	public List<Entry> findAllOfEnneagramCoreType(int type){
	//public List<Entry> findAllOfEnneagramCoreType(){
		//return this.entryRepository.findAllOfEnneagramCoreType();
		List<Entry> entries = this.entryRepository.findAllOfEnneagramCoreType(type);
		return entries;
		//Optional<List<Entry>> entries = this.entryRepository.findAllOfEnneagramCoreType(type);
		//return entries.orElseThrow(ResourceNotFoundException::new);
	}
	
	@Override
	public List<Entry> findAllOfEnneagramCoreType2(){		
		List<Entry> entries = this.entryRepository.findAllOfEnneagramCoreType2();
		return entries;
	}

}
