package com.aptech.group3.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.LessonSubjectRepository;
import com.aptech.group3.Repository.TeacherRegistedRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.TeacherRegisted;
import com.aptech.group3.service.TeacherRegistedService;

import shared.BaseMethod;

@Service
public class TeacherRegistedServiceImpl implements TeacherRegistedService {
	
	

	
	@Autowired private TeacherRegistedRepository teacherRepo;
	
	@Autowired private LessonSubjectRepository lessonRepo;
	
	@Autowired private UserRepository userRepo;
	
	public void update(Long classId,Long teacherId) {
		TeacherRegisted check= teacherRepo.findByClass_registedId(classId);
		
		userRepo.findById(teacherId).ifPresent(check::setTeacher);
		teacherRepo.save(check);
		
	}
	
	
	
	public List<TeacherRegisted> getListClassByDay(Long teacherId, Date day){
		
		List<TeacherRegisted> list= lessonRepo.getLessonByDay(teacherId, day).stream()
				.map(e->{
					TeacherRegisted data= new TeacherRegisted();
					data.setClass_registed(e.getClassSubject());
					data.setSemester(e.getClassSubject().getSemeter());
					data.setTeacher(e.getClassSubject().getTeacher());
					data.setId(teacherRepo.findByClass_registedId(e.getClassSubject().getId()).getId());
					return data;
				}).toList();
		return list;
		
	}
	
	
	/* new method */
	
	//get all list class of teacher by semester
	public List<TeacherRegisted> getByTeacherId(Long id,Long semesterId){
		return teacherRepo.findByTeacherIdAndSemesterId(id,semesterId);
	}
	
	public List<TeacherRegisted> getListClassByDay( Long teacherId,Long semesterId){
		List<TeacherRegisted> data= teacherRepo.findByTeacherIdAndSemesterId(teacherId,semesterId);
		
		List<TeacherRegisted> result= new ArrayList<>();
		
		Date today= new Date();
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		data.forEach(e->{
			Date start= e.getClass_registed().getDateStart();
			Date end = e.getClass_registed().getDateEnd();
			
			if(BaseMethod.customCompareDate(today, start)>=0 
					&& BaseMethod.customCompareDate(today, end)<= 0 &&
					e.getClass_registed().getWeekDay()==dayOfWeek) {
				result.add(e);
				
			}
		
		});
		
		return result;
	}
	
	public boolean checkTeacherOwnClass(Long teacherId, Long classId ) {
		TeacherRegisted check= teacherRepo.checkTeacher(teacherId, classId);
		if(check!=null) {
			return true;
		}else {
			return false;
		}
	}
}
