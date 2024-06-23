package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.SemeterDto;
import com.aptech.group3.Dto.TimeTableDto;
import com.aptech.group3.entity.Semeter;

public interface SemesterService {
	public Semeter getByYearAndName(int year,int name);
	public Semeter getSemesterById(int id);
	public Semeter getCurrentSemester();
	public List<Semeter> findAll();
	public List<TimeTableDto> getListWeek(int id);


	//du
	public Semeter create(SemeterDto dto);
}
