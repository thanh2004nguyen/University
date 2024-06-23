package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.service.SubjectLevelService;
import com.aptech.group3.service.SubjectService;


@RestController
public class subjectApiController {
	
	@Autowired SubjectService subjectService;
	
	@Autowired SubjectLevelService sublvSbervice;
	
	@GetMapping("/api/public/list/subject")
	//@GetMapping("/create")
	public List<Subject> getList( @RequestParam(name = "field") Long fieldId, @RequestParam(name = "level") Long level){
		
		return subjectService.getByFieldIDAndLevel(fieldId, level);
	
	}
}
