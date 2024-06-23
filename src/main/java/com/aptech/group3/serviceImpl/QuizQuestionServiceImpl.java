package com.aptech.group3.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.Dto.QuizQuestionCreateDto;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.service.QuizQuestionService;


@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {

	@Autowired
    private QuizQuestionRepository quizQuestionRepository;
	
	@Autowired
    private QuizRepository quizRepository;
		
	@Autowired
	ModelMapper mapper; 
	
	
	public QuizQuestionCreateDto findQuizQuestionCreateDtoByID(Long id)
	{
		QuizQuestion quizQuestion = quizQuestionRepository.getById(id);
		QuizQuestionCreateDto dto = mapper.map(quizQuestion, QuizQuestionCreateDto.class);

		return dto  ;
	}
	
	

    public Page<QuizQuestion> findPaginatedQuestions(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return quizQuestionRepository.findAll(pageRequest);
    }
    
    // New method to find paginated questions by Quiz ID
    public Page<QuizQuestion> findPaginatedQuestionsByQuizId(Long quizId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return quizQuestionRepository.findByQuizId(quizId, pageRequest);
    }
    
    public QuizQuestion create(QuizQuestionCreateDto dto)
    {
    	try {
    	  	QuizQuestion question = mapper.map(dto, QuizQuestion.class);
    		quizRepository.findById(dto.getQuizId()).ifPresent(question::setQuiz);
    		return quizQuestionRepository.save(question);
			
		} catch (Exception e) {
			 throw e;
		}
  
    	
    }
    
    
    
    
    
    
    public List<QuizQuestion> findListQuestionByQuizId(Long quizId)
    {
    	 return quizQuestionRepository.findByQuizId(quizId);
    }
    
    public float findCurrentMarkOfQuiz(Long quizId)
    {
    	 List<QuizQuestion> listQuestions = this.findListQuestionByQuizId(quizId);
         float currentMark = 0;
   	     if(!listQuestions.isEmpty())
   	     {
   		     for(QuizQuestion a :listQuestions )
   		     {
   		    	float mark= a.getMark();
   		    	currentMark += mark;
   		     }	 
   	     }
   	     return currentMark;
    }
    
    
    
}
