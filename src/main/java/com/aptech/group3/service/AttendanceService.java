package com.aptech.group3.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.entity.Attendance;

public interface AttendanceService {

	public void create(Long studentId,Long lessonId,String status);
	
	public List<AttendanceDto> getListAttendanceDto(Long lessonId, Date day);
	
	public Attendance getDetailAttendance(Long studentId,Long classID, Date date);
	
	public  List<Attendance> findByClassAndStudent(Long classId, Long StudentId);
	
}
