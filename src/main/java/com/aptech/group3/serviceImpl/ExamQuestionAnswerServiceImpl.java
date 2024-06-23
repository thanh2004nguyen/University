package com.aptech.group3.serviceImpl;


import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.service.ExamQuestionAnswerService;


@Service
public class ExamQuestionAnswerServiceImpl implements ExamQuestionAnswerService {
	@Autowired
	private ExamQuestionAnswerRepository examQuestionAnswerRepository;
	@Autowired
	private ModelMapper mapper;
     
	
	public List<ExamQuestionAnswer> findByQuestionID(Long id)
	{
		return examQuestionAnswerRepository.findByQuizQuestionId(id);
	}
	
	public List<ExamQuestionAnswer> findByExamID(Long id)
	{
		return examQuestionAnswerRepository.findByQuizExamId(id);
	}
	
	public List<ExamQuestionAnswer> findByQuizExamIdAndQuestionId(Long quizExamId,Long quizQuestionId)
	{
		return examQuestionAnswerRepository.findByQuizExamIdAndQuizQuestionId(quizExamId, quizQuestionId);
	}
}
