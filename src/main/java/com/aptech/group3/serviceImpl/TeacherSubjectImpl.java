package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.TeacherSubjectRepository;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.TeacherSubjectService;

@Service
public class TeacherSubjectImpl implements TeacherSubjectService {

	@Autowired 
	TeacherSubjectRepository tr ;
	
	public List<User> getAvailableTeacher(int id, int weekday,int start, Date dstart, Date dend) {
		List<User> data = tr.getAvailableTeacher(id, weekday, start, dstart, dend);
		return data;
	}
}
