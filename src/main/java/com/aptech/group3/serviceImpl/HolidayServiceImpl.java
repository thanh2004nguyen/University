package com.aptech.group3.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.Dto.HolidayEditDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.HolidayRepository;
import com.aptech.group3.Repository.LessonSubjectRepository;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.service.HolidayService;


import shared.BaseMethod;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired private  HolidayRepository repo;
	@Autowired private ClassForSubjectRepository classRepo;

	@Autowired private ModelMapper mapper;
	
	public void delete(Long holidayId) {
		Holiday data= repo.findById(holidayId).orElse(null);
		
	
		
		repo.delete(data);

	}
	
	public Page<Holiday> getListByYear(int year,Pageable pageable){
	return	repo.findByYear(year,pageable);
	}
	
	public List<Holiday> getHolidayByYear(int year){
		return repo.findByYear(year);
	}
	
	public Holiday getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	
	public boolean edit ( HolidayEditDto dto) {
		Holiday data=repo.findById(dto.getId()).orElse(null);
	
		int cws = BaseMethod.customCompareDate(data.getDateStart(), dto.getDateStart());
		int cwe = BaseMethod.customCompareDate(data.getDateEnd(),dto.getDateEnd());
		
		
/*	khong thay doi ngay thang*/
				if( cws==0 && cwe==0) {
						data.setName(dto.getName());
						if(dto.getClassId()!=null) {
							classRepo.findById(dto.getClassId()).ifPresent(data::setClassSubject);	
						}
						repo.save(data);
						
					return false;
				}else {
				data.setDateStart(dto.getDateStart());
				data.setDateEnd(dto.getDateEnd());
				repo.save(data);
					
			return true;
				}
		}
		
	
	
	public  HolidayEditDto getListEditDto(Long id) {
		Holiday data=repo.findById(id).orElse(null);
		
		HolidayEditDto dto= mapper.map(data,HolidayEditDto.class );
		dto.setDateStart(data.getDateStart());
		dto.setDateEnd(data.getDateEnd());
		dto.setType(BaseMethod.customCompareDate(data.getDateEnd(), data.getDateStart())!=0 ? "many" : "one");
		
		
		if(data.getClassSubject()!= null) {
			dto.setClassId(data.getClassSubject().getId());
		}
		return  dto;
	}
	
	
	public HolidayEditDto create(HolidayCreateDto dto) {

	Holiday h = mapper.map(dto, Holiday.class);
	h.setDateStart(dto.getDateStart());
	h.setDateEnd(dto.getDateEnd());
	if(dto.getClassIdHoliday()!=null) {
	classRepo.findById(dto.getClassIdHoliday()).ifPresent(h::setClassSubject);
}
	Holiday result= repo.save(h); 
	HolidayEditDto data= mapper.map(dto, HolidayEditDto.class);
	data.setId(result.getId());
	return data;

}
		
		
	
}
