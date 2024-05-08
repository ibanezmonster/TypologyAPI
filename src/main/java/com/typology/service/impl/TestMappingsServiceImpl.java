package com.typology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.typology.entity.TestMappingsEntity;
import com.typology.repository.TestMappingsRepository;
import com.typology.service.TestMappingsService;

@Service
public class TestMappingsServiceImpl implements TestMappingsService
{
	@Autowired
	TestMappingsRepository testRepository;
	
	@Override
	public TestMappingsEntity saveTestEntity(TestMappingsEntity testEntity) {
		return testRepository.save(testEntity);
	}
}
