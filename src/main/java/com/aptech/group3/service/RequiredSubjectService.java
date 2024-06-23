package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.RequiredSubjectDto;
import com.aptech.group3.entity.RequiredSubject;

public interface RequiredSubjectService {
	
	  public List<RequiredSubject> findListRequiredSubjectBySubjectId(Long subjectId);
	  
	  
	  
	public RequiredSubject findById(Long id);
	public RequiredSubject getStatus(String status);

	public List<RequiredSubject> findAll();
	public void createreq(RequiredSubjectDto dto);
	public void update(RequiredSubjectDto dto);
	
	
	
}
