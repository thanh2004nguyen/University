package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizExam;

public interface QuizExamRepository extends JpaRepository<QuizExam, Long> {
	public List<QuizExam> findByClassForSubjectId(Long classId);

	public QuizExam findByStudentIdAndQuizId(Long studentId, Long quizId);

	public List<QuizExam> findByStudentIdAndClassForSubjectId(Long studentId, Long classId);

	@Query("SELECT c FROM QuizExam c " + "WHERE c.student.id = :studentId "
			+ "AND :date BETWEEN c.startFromDate AND c.closeDate")
	
	List<QuizExam> findByStudentIdAndDateBetweenRegistration(
			@Param("studentId") Long studentId,
			@Param("date") Date date);
	 
}
