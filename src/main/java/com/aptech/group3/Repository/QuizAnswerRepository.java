package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.aptech.group3.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
          public List<QuizAnswer> findByQuizQuestionId(Long id);
}
