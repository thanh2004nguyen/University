package com.aptech.group3.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.aptech.group3.Dto.QuizQuestionCreateDto;
import com.aptech.group3.entity.QuizQuestion;

public interface QuizQuestionService {
	 public Page<QuizQuestion> findPaginatedQuestions(int page, int pageSize);
	 public Page<QuizQuestion> findPaginatedQuestionsByQuizId(Long quizId, int page, int pageSize);
	 public QuizQuestion create(QuizQuestionCreateDto dto);
	 public List<QuizQuestion> findListQuestionByQuizId(Long quizId);
	 public float findCurrentMarkOfQuiz(Long quizId);
}
