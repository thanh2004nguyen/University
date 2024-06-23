package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.ExamQuestionAnswer;




public interface ExamQuestionAnswerRepository extends JpaRepository<ExamQuestionAnswer, Long> {

	    public List<ExamQuestionAnswer> findByQuizQuestionId(Long questionId);
	    public List<ExamQuestionAnswer> findByQuizExamId(Long examId);
	    
	    public List<ExamQuestionAnswer> findByQuizExamIdAndQuizQuestionId(Long quizExamId,Long quizQuestionId);
	    
}
