package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aptech.group3.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
	Page<QuizQuestion> findByQuizId(Long quizId, Pageable pageable);
	
	List<QuizQuestion> findByQuizId(Long quizId);
}
