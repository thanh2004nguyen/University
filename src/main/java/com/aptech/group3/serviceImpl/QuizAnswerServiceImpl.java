package com.aptech.group3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizQuestionService;


@Service
public class QuizAnswerServiceImpl implements QuizAnswerService {

	@Autowired
    private QuizAnswerRepository quizAnswerRepository;
	
	@Autowired
    private QuizQuestionRepository questionRepository;


    
    // New method to find paginated questions by Quiz ID
    public List<QuizAnswer> findAnswerByQuestionId(Long quizId) {
  
        return quizAnswerRepository.findByQuizQuestionId(quizId);
    }
    
    
 
    
    
}
