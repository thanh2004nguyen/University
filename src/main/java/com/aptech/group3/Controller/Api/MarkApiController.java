package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.MarkApiDto;
import com.aptech.group3.Dto.MarkDetailDto;
import com.aptech.group3.service.MarkSubjectService;

@RestController
public class MarkApiController {

	
	@Autowired
	private MarkSubjectService service;
	
	@GetMapping("/api/mark/{id}")
	public ResponseEntity<List<MarkApiDto>>  showListMark( @PathVariable(name = "id") Long studentId) 
	{
		
		List<MarkApiDto> data= service.getListMarkDto(studentId);
		return ResponseEntity.ok().body(data);
	}
	
	@GetMapping("/api/mark/detail/{id}/{class}")
	public ResponseEntity<MarkDetailDto>  showDetailMark( @PathVariable(name = "id") Long studentId,
			@PathVariable(name = "class") Long classId	) 
	{
		
		MarkDetailDto data= service.getMarkDetailDto(studentId, classId);
		return ResponseEntity.ok().body(data);
	}
	
	
	
}
