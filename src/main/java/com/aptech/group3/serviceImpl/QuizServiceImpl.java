package com.aptech.group3.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.QuizService;
import com.aptech.group3.service.SubjectService;

import shared.BaseMethod;

@Service
public class QuizServiceImpl implements QuizService {
               
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ModelMapper mapper;
	
	public QuizCreateDto findQuizCreateDtoByID(Long id)
	{
		Quiz quiz = quizRepository.getById(id);
		Subject subject = subjectRepository.getById(quiz.getSubject().getId());
		QuizCreateDto dto = mapper.map(quiz, QuizCreateDto.class);
		dto.setSubjectName(subject.getName());
		return dto  ;
	}
	
	public List<Quiz> findQuizsByTeacherId(Long teacherId)
	{
		return quizRepository.findByTeacherId(teacherId);
	}
	
    @Transactional
    public Quiz update(QuizCreateDto dto ,Long updateId) {
        try {
                 
        	 Quiz quiz = quizRepository.getById(updateId);
        	 Subject sub=  subjectService.findByName(dto.getSubjectName());
        	 quiz.setDuration(dto.getDuration());
        	 quiz.setName(dto.getName());
        	 quiz.setSubject(sub);
        	 quiz.setTotalMark(dto.getTotalMark());
        	 quiz.setType(dto.getType());
     
             return quizRepository.save(quiz); 
       
        } catch (Exception ex) {
 
            throw ex;
        }
    }

	    @Transactional
	    public Quiz create(QuizCreateDto dto ,Long teacherId) {
	        try {

	            Quiz quiz = mapper.map(dto, Quiz.class);
	            Subject sub=  subjectService.findByName(dto.getSubjectName());
	            User teacher = userRepository.getById(teacherId);
	            quiz.setTeacher(teacher);
	            quiz.setSubject(sub);
	            quiz.setStatus("Incomplete");
	            return quizRepository.save(quiz); 
	       
	        } catch (Exception ex) {
	 
	            throw ex;
	        }
	    }
	    
	    public List<Quiz> findListQuizBySubjectIdAndTeacherId(Long subjectId,Long teacherId,String status)
	    {
	    	return quizRepository.findBySubjectIdAndTeacherIdAndStatus(subjectId, teacherId,status);
	    }
	    
	    public Page<Quiz> findListQuizByTeacherId(Long teacherId,Pageable page)
	    {
	    	return quizRepository.findByTeacherId(teacherId,page );
	    }
	    
}
