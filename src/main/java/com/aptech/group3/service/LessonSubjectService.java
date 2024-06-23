package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.Dto.HolidayEditDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.LessonSubject;

public interface LessonSubjectService {
	
	public List<LessonSubject> getLessonsByClassSubjectId(Long classSubjectId);
	
	public void deleteLessonByClassId(Long classId);

	List<LessonSubject>  updateLesson(HolidayEditDto dto);
	
	public void CheckOrRemove(HolidayEditDto dto);
	
	public void remove(Long holidayId);
	

	public void create(ClassForSubject subject);
	
	public List< LessonSubject> getCurrentLesson(Long classId, Date day);
	public List<LessonSubject> getByClassId(Long id);
	

	
	
}
