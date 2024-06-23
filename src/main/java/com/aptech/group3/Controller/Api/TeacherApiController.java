package com.aptech.group3.Controller.Api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.User;
import com.aptech.group3.service.TeacherSubjectService;

import shared.BaseMethod;

@RestController
@RequestMapping({"/api"})
public class TeacherApiController {
	
	@Autowired
	TeacherSubjectService teacherService;
	
	@GetMapping("/public/teacher/available")
	public List<User> teacherAvailable(@RequestParam("id")int id, @RequestParam("weekday")int weekday,
			@RequestParam("start")int start, @RequestParam("dstart")String dstart, @RequestParam("dend")String dend){
		 Date startd=BaseMethod.convertDate(dstart);
		 Date endd=BaseMethod.convertDate(dend);
		 
		List<User> data=teacherService.getAvailableTeacher(id, weekday, start, startd, endd);
		return data;
	}

	
}
