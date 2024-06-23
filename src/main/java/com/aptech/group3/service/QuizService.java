package com.aptech.group3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.entity.Quiz;

@Service
public interface QuizService {
	  public QuizCreateDto findQuizCreateDtoByID(Long id);
	  public Quiz create(QuizCreateDto dto ,Long teacherId);
	  public List<Quiz> findListQuizBySubjectIdAndTeacherId(Long subjectId,Long teacherId,String status);
	  public List<Quiz> findQuizsByTeacherId(Long teacherId);  
	  public Quiz update(QuizCreateDto dto ,Long updateId) ;
	  public Page<Quiz> findListQuizByTeacherId(Long teacherId,Pageable page);
}
