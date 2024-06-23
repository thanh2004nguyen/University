package com.aptech.group3.Controller.Api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.StudentClassApiDto;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.HolidayService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import shared.BaseMethod;

@RestController
@RequestMapping({ "/api" })
public class ClassSubjectApi {

	@Autowired private HolidayService holidayService;

	@Autowired private SemesterService semeterService;
	
	@Autowired private StudentClassService classService;
	
	@Autowired private ClassForSubjectService classForSubjectService;
	
	
	//api for mbile app
	@GetMapping("/class/list/{id}")
	public  ResponseEntity< List<StudentClassApiDto>> getlistStudyInSemester(@PathVariable("id") Long studentId) 
	{
		Semeter currentSemester = semeterService.getCurrentSemester();
		
		List<StudentClassApiDto> data= classService.getCurrentClassList(studentId, currentSemester.getId());
		
		return  ResponseEntity.ok().body(data);
		
	}

	//api for web
	@GetMapping("/public/field/list/{id}")
	public List<ClassForSubject> getListClass(@PathVariable("id") Long fieldId){
		
		Semeter currentSemester = semeterService.getCurrentSemester();
		
		List<ClassForSubject> data= classForSubjectService.findBySemesterIdAndFieldId(currentSemester.getId(), fieldId);
		
		return data;
	}
	//new 
	@GetMapping("/public/class/dateend")
	public Date getDateEnd(@RequestParam(name = "start") String start, @RequestParam(name = "type") String type) 
	{

		Date startDate = BaseMethod.convertDate(start);
		
	
		Semeter currentSemester = semeterService.getCurrentSemester();

		List<Holiday> listHoliday = holidayService.getHolidayByYear(currentSemester.getYear());


		Calendar endate =  Calendar.getInstance();
		endate.setTime(startDate);
		if (type.equals("all")) {
			endate.add(Calendar.DATE, 16*7+1);
		}

		if (type.equals("fhalf") || type.equals("lhalf")) {
			endate.add(Calendar.DATE, 8*7+1);
		}

		
		
		  listHoliday.forEach(e -> { int cws =
		  BaseMethod.customCompareDate(e.getDateStart(), startDate); int cwe =
		  BaseMethod.customCompareDate(e.getDateEnd(), endate.getTime());
		  
		  if (cws >= 0 && cwe <= 0 && BaseMethod.getWeekDay(startDate) ==
		  BaseMethod.getWeekDay(e.getDateStart())) { 
				endate.add(Calendar.DATE, 7);
		  }
		  });
		 
		return endate.getTime();
	}
}
