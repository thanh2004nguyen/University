package com.aptech.group3.Repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	
	List<Quiz> findBySubjectIdAndTeacherIdAndStatus(Long subjectId,Long teacherId,String status);
	List<Quiz> findByTeacherId(Long teacherId);
	Page<Quiz> findByTeacherId(Long teacherId, Pageable page);


}
