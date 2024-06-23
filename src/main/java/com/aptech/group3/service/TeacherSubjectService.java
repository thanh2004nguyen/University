package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.entity.User;

public interface TeacherSubjectService  {
	public List<User> getAvailableTeacher(int id, int weekday,int start, Date dstart, Date dend);
}
