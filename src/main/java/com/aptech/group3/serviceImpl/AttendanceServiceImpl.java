package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.Repository.AttendanceRepository;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.LessonSubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.service.AttendanceService;

import shared.BaseMethod;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceRepository repo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	LessonSubjectRepository lessonRepo;
	
	
	public  List<Attendance> findByClassAndStudent(Long classId, Long StudentId){
		return repo.findByClassAndStudent( classId,StudentId);
	}
	
	public Attendance getDetailAttendance(Long studentId,Long classID, Date date) {
		return repo.findDetailAttendance(studentId,classID,date);
	}

	public void create(Long studentId, Long lessonId, String status) {
		Date day = new Date();
		Attendance check = repo.findByStudent_IdAndLesson_IdAndDay(lessonId, studentId, day);
		if (check == null) {
			Attendance data = new Attendance();
			data.setDay(day);
			data.setStatus(status);
			userRepo.findById(studentId).ifPresent(data::setStudent);
			lessonRepo.findById(lessonId).ifPresent(data::setLesson);
			repo.save(data);
		} else {

			check.setStatus(status);
			repo.save(check);
		}
	}

	public List<AttendanceDto> getListAttendanceDto(Long classId, Date day) {
		List<AttendanceDto> data = repo.findByClassSubject_IdAndDay(classId, day).stream().map(e -> {
			AttendanceDto dto = new AttendanceDto();
			dto.setStatus(e.getStatus());
			dto.setStudent_name(e.getStudent().getName());
			dto.setStudent_id(e.getStudent().getId());
			dto.setStudent_code(e.getStudent().getCode());
			return dto;
		}).toList();
		return data;
	}

}
