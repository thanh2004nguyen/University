package com.aptech.group3.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizQuestion;

public interface QuizAnswerService {
	   public List<QuizAnswer> findAnswerByQuestionId(Long quizId);
}
