package com.aptech.group3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.Dto.HolidayEditDto;
import com.aptech.group3.entity.Holiday;

public interface HolidayService {

	public void delete(Long holidayId);
	
	public List<Holiday> getHolidayByYear(int year);
	
	public HolidayEditDto create(HolidayCreateDto dto);
	
	public Holiday getById(Long id);
	
	public  HolidayEditDto getListEditDto(Long id);
	
	public boolean edit ( HolidayEditDto dto);
	
	public Page<Holiday> getListByYear(int year,Pageable pageable);
}
