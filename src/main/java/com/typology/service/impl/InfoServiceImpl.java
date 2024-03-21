package com.typology.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.typology.entity.info.Teacher;
import com.typology.repository.TeacherRepository;
import com.typology.service.InfoService;

@Service
@Transactional
public class InfoServiceImpl implements InfoService
{
	@Autowired
	TeacherRepository teacherRepository;
	
	@Override
	public List<Teacher> getTeachers(){		
		return teacherRepository.findAll();
	}
	
//	public List<TypologySystem> getTypologySystems(){
//		return infoRepository.getTypologySystems();
//	}
}
