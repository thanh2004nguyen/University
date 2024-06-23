package com.aptech.group3.Controller.Api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.AttendanceCreateDto;
import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.Dto.DetailAttendClassDto;
import com.aptech.group3.Dto.LessonApiDto;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.service.AttendanceService;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.LessonSubjectService;

@RestController
@RequestMapping({ "/api/attendance" })
public class AttendanceApiController {

	@Autowired
	AttendanceService attendanceService;
	@Autowired
	LessonSubjectService lessonService;
	@Autowired
	ClassForSubjectService classService;
	
	//api for web
	@PostMapping("/create/{id}")
	public void create(@PathVariable(name = "id") Long lessonId, @RequestBody AttendanceCreateDto dto) {
		attendanceService.create(dto.getStudentId(), lessonId, dto.getStatus());
	}

	@GetMapping("/test")
	public List<AttendanceDto> test() {
		Long classId = (long) 22;
		Date day = new Date();
		List<AttendanceDto> data = attendanceService.getListAttendanceDto(classId, day);
		return data;

	}

	
	//api for mobile
	@GetMapping("/detail")
	public DetailAttendClassDto detailAttendClass(@RequestParam("id") Long classID,
			@RequestParam("student") Long studentId) {

		DetailAttendClassDto result = new DetailAttendClassDto();

		List<LessonSubject> lessons = lessonService.getByClassId(classID);

		List<Attendance> attendances = attendanceService.findByClassAndStudent(classID, studentId);

		result.setClassName(lessons.get(0).getClassSubject().getName());
		result.setSubjectName(lessons.get(0).getClassSubject().getSubject().getName());
		List<LessonApiDto> listLesson = new ArrayList<LessonApiDto>();

		
		lessons.forEach(l -> {
			LessonApiDto lessDto = new LessonApiDto();
			lessDto.setLesson(l.getLesson());
			lessDto.setDay(l.getDay());
			
			attendances.forEach(a -> {
				
				if (a.getLesson().getId() == l.getId()) {
					
					lessDto.setStatus(a.getStatus());
					System.out.println(a.getStatus());

				} 

			});

			listLesson.add(lessDto);

		});
		
		result.setLessons(listLesson);
		
	
		

		return result;

	}

}
