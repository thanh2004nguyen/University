package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.ExamQuestionAnswer;

public interface ExamQuestionAnswerService {
	public List<ExamQuestionAnswer> findByQuestionID(Long id);
	public List<ExamQuestionAnswer> findByExamID(Long id);
	public List<ExamQuestionAnswer> findByQuizExamIdAndQuestionId(Long quizExamId,Long quizQuestionId);
}
