package com.aptech.group3.Controller.Api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.TimeTableDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.service.AttendanceService;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;

import shared.BaseMethod;

@RestController
@RequestMapping({"/api"})
public class SemesterController {
	
	
	@Autowired
	SubjectService ss;
	
	@Autowired
	SemesterService semesterService;
	
	@Autowired AttendanceService attendanceService;
	
	@Autowired
	LessonSubjectService lessonService;
	
	@Autowired
	StudentClassService service;
	
	@GetMapping("/credit")
	public int getCreditOfSubject( @RequestParam("id") int id) {
		return ss.getCredit(id);
	}
	

	
	


}
