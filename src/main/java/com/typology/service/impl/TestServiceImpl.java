package com.typology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.typology.entity.TestEntity;
import com.typology.repository.TestRepository;
import com.typology.service.TestService;

@Service
public class TestServiceImpl implements TestService
{
	@Autowired
	TestRepository testRepository;
	
	@Override
	public TestEntity saveTestEntity(TestEntity testEntity) {
		return testRepository.save(testEntity);
	}
}
