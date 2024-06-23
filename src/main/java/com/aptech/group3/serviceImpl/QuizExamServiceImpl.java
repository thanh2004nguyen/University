package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizExam;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizQuestionService;


@Service
public class QuizExamServiceImpl implements QuizExamService {

     @Autowired
	 private QuizExamRepository quizExamRepository;
	 @Autowired
	 private ExamQuestionAnswerService examQuestionAnswerService;
	 @Autowired
	 private QuizAnswerService quizAnswerService;
	 
		public List<QuizExam> findExamByClassId(Long classId)
		{
			return quizExamRepository.findByClassForSubjectId(classId);
		}
		
      
	public QuizExam findExamByStudentIdAndQuizId(Long id ,Long quizId)
	{
		return quizExamRepository.findByStudentIdAndQuizId(id, quizId);
	}
	
	
	public List<QuizExam> findExamByStudentIdAndClassId(Long studentId ,Long classId)
	{
		return quizExamRepository.findByStudentIdAndClassForSubjectId(studentId, classId);
	}
	
	public List<QuizExam> findExamByStudentIdAndDate(Long studentId ,Date date)
	{
		return quizExamRepository.findByStudentIdAndDateBetweenRegistration(studentId, date);
	}
	
	
	
	public float canculateExamMark(Long quizExamId)
	{
		List<ExamQuestionAnswer> listExamQuestionAnswer = examQuestionAnswerService.findByExamID(quizExamId);
 	   Float mark = (float) 0;
 	for(ExamQuestionAnswer l :listExamQuestionAnswer )
 	{
 	   Float maxMarkPerQuestion = l.getQuizQuestion().getMark();
 	
 	   List<ExamQuestionAnswer> ListAnswers = examQuestionAnswerService.findByQuestionID(l.getQuizQuestion().getId());
 	   if(l.getQuizQuestion().getType().equals("Single"))
 	   {
 	
 		      int correct = ListAnswers.get(0).getQuizAnswer().getIsTrue();
 		      if(correct == 1)
 		      {
 		    	  mark += maxMarkPerQuestion;
 		      }
 	   }
 	   else
 	   {
 		   List<QuizAnswer> listtOriginAnswers = quizAnswerService.findAnswerByQuestionId(l.getQuizQuestion().getId());
 		   int countOrigin = 0;
 		   int countAnswer = 0;
 		   for(QuizAnswer aa:listtOriginAnswers)
 		   {
 			   if(aa.getIsTrue() == 1)
 			   {
 				   countOrigin += 1;
 			   }
 		   }
 		   float markEachAnswer = (float)(maxMarkPerQuestion/countOrigin);	       
 		   for(ExamQuestionAnswer bb: ListAnswers )
 		   {
 			   if(bb.getQuizAnswer().getIsTrue()==1)
 			   {
 				   countAnswer +=1;
 			   }
 		   }
 		   if((ListAnswers.size()-countAnswer) >= countAnswer ) 
 		        {
				       mark +=0;
		          	}
 		   if((ListAnswers.size()-countAnswer) < countAnswer)
		          		
		          	{
 			       int totalCorrectAnswer = (countAnswer - (ListAnswers.size()-countAnswer));
		          		mark += totalCorrectAnswer*markEachAnswer/(ListAnswers.size())   ;

             	  }
 	     }
 	}
 	
 	   return mark;
	}
	
	
}
