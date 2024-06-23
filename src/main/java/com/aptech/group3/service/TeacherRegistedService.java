package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.entity.TeacherRegisted;

public interface TeacherRegistedService {

	public List<TeacherRegisted> getListClassByDay(Long teacherId, Date day);
	
	public void update(Long classId,Long teacherId);
	
	
	/* new method */
	public List<TeacherRegisted> getByTeacherId(Long id, Long semesterId);

	public boolean checkTeacherOwnClass(Long teacherId, Long classId);

	public List<TeacherRegisted> getListClassByDay(Long teacherId, Long semesterId);
}
