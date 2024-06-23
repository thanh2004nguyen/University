package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.entity.QuizExam;

public interface QuizExamService {
	public List<QuizExam> findExamByClassId(Long classId);
	public QuizExam findExamByStudentIdAndQuizId(Long id ,Long quizId);
	public List<QuizExam> findExamByStudentIdAndClassId(Long studentId ,Long classId);
	public float canculateExamMark(Long quizExamId);
	public List<QuizExam> findExamByStudentIdAndDate(Long studentId ,Date date);
}
