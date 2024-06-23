package com.aptech.group3.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectBasicDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.QuizClassRepository;
import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.QuizClass;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.QuizClassService;

import shared.BaseMethod;

@Service
public class QuizClassServiceImpl implements QuizClassService {
    
	@Autowired
	private QuizClassRepository quizClassRepository;
	
	public List<QuizClass> FindQuizByCLassId(long id)
	{
		return quizClassRepository.findByClassForSubjectId(id);
	}
}
