package com.typology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.info.Teacher;
import com.typology.service.EntryService;
import com.typology.service.InfoService;

import lombok.AllArgsConstructor;

@RequestMapping("/api/info")
@RestController
@AllArgsConstructor
public class InfoController
{
	@Autowired
	InfoService infoService;
	
	@GetMapping("/teachers")
    @ResponseStatus(HttpStatus.OK)
	public List<Teacher> getTeachers(){
		return infoService.getTeachers();
	}
//	
//	
//	@GetMapping("/typology_systems")
//    @ResponseStatus(HttpStatus.OK)
//	public List<Teacher> getTypologySystems(){
//		//return entryService.createEntry(entry);
//	}
}
