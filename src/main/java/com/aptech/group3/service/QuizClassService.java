package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.QuizClass;

public interface QuizClassService {

	public List<QuizClass> FindQuizByCLassId(long id);
	
}
