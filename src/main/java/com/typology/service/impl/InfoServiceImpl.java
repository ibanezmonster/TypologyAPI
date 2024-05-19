package com.typology.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.typology.entity.user.Typist;
import com.typology.repository.TypistRepository;
import com.typology.service.InfoService;

@Service
@Transactional
public class InfoServiceImpl implements InfoService
{
	@Autowired
	TypistRepository typistRepository;
	
	
	
	public InfoServiceImpl(TypistRepository typistRepository)
	{
		this.typistRepository = typistRepository;
	}



	@Override
	public List<Typist> getTypists(){		
		return typistRepository.findAll();
	}
	
//	public List<TypologySystem> getTypologySystems(){
//		return infoRepository.getTypologySystems();
//	}
}
