package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.TimeTableApiDto;
import com.aptech.group3.service.StudentClassService;

import shared.BaseMethod;

@RestController
@RequestMapping({"/api/timetable"})
public class TimeTableApiController {
	
	@Autowired StudentClassService service;

	@GetMapping("/show")
	public ResponseEntity<List<TimeTableApiDto>> getScheduleByDay(@RequestParam("id")Long id,
			@RequestParam("day")String day){
		
		List<TimeTableApiDto> data= service.getTimtableApiDto(id, BaseMethod.convertDate(day));
		
		return ResponseEntity.ok().body(data);
	}
}
