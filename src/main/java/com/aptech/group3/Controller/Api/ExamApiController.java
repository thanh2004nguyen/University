package com.aptech.group3.Controller.Api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.MarkSubjectRepository;
import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizExam;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.LoginRequest;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.MarkSubjectService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizService;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.serviceImpl.JwtTokenProvider;
import com.aptech.group3.serviceImpl.StudentClassServiceImpl;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")

public class ExamApiController {
	
	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;
	


@Autowired
private QuizRepository quizRepository;



@Autowired
AuthenticationManager authenticationManager;



@Autowired
private StudentClassServiceImpl studentsubservice;


@Autowired
private QuizAnswerService quizAnswerService;

@Autowired
private ExamQuestionAnswerService examQuestionAnswerService;
@Autowired
private ExamQuestionAnswerRepository examQuestionAnswerRepository;

@Autowired
private QuizQuestionRepository quizQuestionRepository;
@Autowired
private QuizAnswerRepository quizAnswerRepository;

@Autowired
private QuizExamRepository quizExamRepository;

@Autowired
private QuizExamService quizExamService;


@Autowired
private QuizService quizService;


@Autowired
private FiledRepository filedRepository;
@Autowired
private SubjectLevelRepository subjectLevelRepository;

@Autowired
private SubjectService subService;

@Autowired
private MarkSubjectService markSubjectService;





@Autowired
private MarkSubjectRepository markSubjectRepository;

@GetMapping("/api/public/listQuizBySubject")
@ResponseBody
public List<Quiz> showListQuizBySubjectAndTecher(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
        ClassForSubject currentClass = classForSubjectRepository.getById(classId);
	    return quizService.findListQuizBySubjectIdAndTeacherId(currentClass.getSubject().getId(), currentUser.getUserId(),"Finished");
}


@GetMapping("/api/public/listAlreadyApplied")
@ResponseBody
public List<QuizExam> ListQuizAlreeadyApplied(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
     
	    return quizExamService.findExamByClassId(classId);
}

@PostMapping("/api/Quiz/Submit")
@ResponseBody
public void QuizSubmit(@RequestBody Map<String, Object> requestBody) {
	
	String typeRequet = requestBody.get("type").toString();
	Long quizExamId = Long.valueOf(requestBody.get("quizExamId").toString());
    @SuppressWarnings("unchecked")
	Map<String, List<Integer>> answers = (Map<String, List<Integer>>) requestBody.get("answers");
    Long currentQuestionId = null;
    List<Long> answerIds = null;
    
    // Take question Id and answer Id List from client
    for (Map.Entry<String, List<Integer>> entry : answers.entrySet()) {
         currentQuestionId = Long.valueOf(entry.getKey());
         answerIds = entry.getValue().stream()
                 .map(Long::valueOf)
                 .collect(Collectors.toList());
    }
    
    
    // after having List answers , save new answers to database ,
        // first delete all old answers
    List<ExamQuestionAnswer> listneedtodelete = examQuestionAnswerService.findByQuizExamIdAndQuestionId(quizExamId, currentQuestionId);  
        for( ExamQuestionAnswer answer : listneedtodelete)
        {
        		 examQuestionAnswerRepository.delete(answer);
        	      
        }
               
  	  QuizExam exam =  quizExamRepository.findById(quizExamId).orElse(null); 	 
	  QuizQuestion question =quizQuestionRepository.findById(currentQuestionId).orElse(null);
	  
    for(Long answerId: answerIds)
    {
    	ExamQuestionAnswer savedAnswer = new ExamQuestionAnswer();
	    QuizAnswer answer = quizAnswerRepository.findById(answerId).orElse(null);
	    savedAnswer.setQuizAnswer(answer);
	    savedAnswer.setQuizExam(exam);
	    savedAnswer.setQuizQuestion(question);
	    examQuestionAnswerRepository.save(savedAnswer);
    }
    
    //canculate mark and update into exam    
    Float markFloat = quizExamService.canculateExamMark(quizExamId);
	exam.setTotalMark(markFloat);
	
	if(typeRequet.equals("submit"))
	{
		exam.setStatus("Submitted");
		
       	MarkSubject saveMarkClass = new MarkSubject();
    	saveMarkClass.setMark(markFloat);
    	saveMarkClass.setUser(exam.getStudent());
    	saveMarkClass.setSubject(exam.getQuiz().getSubject());
    	saveMarkClass.setClassSubject(exam.getClassForSubject());
    	if(exam.getQuiz().getType().equals("1"))
    	{
    		saveMarkClass.setStyle("normalMark");
    	}
    	else if (exam.getQuiz().getType().equals("2")) {
    		saveMarkClass.setStyle("middleMark");
		}
       	else if (exam.getQuiz().getType().equals("3")) {
    		saveMarkClass.setStyle("finalMark");
		}
    	markSubjectRepository.save(saveMarkClass);
		
	}
	quizExamRepository.save(exam);
	
	List<MarkSubject> listMarkSubjects = markSubjectService.getListMarkSubjectByStudentIdAndClassId(exam.getStudent().getId(), exam.getClassForSubject().getId());
	float firstMark=0;
	float middleMark =0;
	float finalMark =0;
	Long finalMarkSUbjectLong = (long) 0;

	if(!listMarkSubjects.isEmpty())
	{	        
		for(MarkSubject aMarkSubject : listMarkSubjects)
		{
			if(aMarkSubject.getStyle().equals("normalMark"))
			{
				firstMark += aMarkSubject.getMark();
			}
			else if(aMarkSubject.getStyle().equals("middleMark"))
			{
				middleMark += aMarkSubject.getMark();
			}
			else if(aMarkSubject.getStyle().equals("finalMark"))
			{
				finalMark += aMarkSubject.getMark();
			}
			
			if(aMarkSubject.getStyle().equals("final"))
			{
				finalMarkSUbjectLong = aMarkSubject.getId();
		    }
			
		}
		if(finalMarkSUbjectLong!=0)
		{
			MarkSubject updateMarkSubject = markSubjectRepository.getById(finalMarkSUbjectLong);
			updateMarkSubject.setMark((float) (0.2*firstMark + 0.2*middleMark + 0.6*finalMark));
			markSubjectRepository.save(updateMarkSubject);
		}
		else {
			MarkSubject  updateMarkSubject = new MarkSubject();
			updateMarkSubject.setMark((float) (0.2*firstMark + 0.2*middleMark + 0.6*finalMark));	    	
			updateMarkSubject.setUser(exam.getStudent());
			updateMarkSubject.setSubject(exam.getQuiz().getSubject());
			updateMarkSubject.setClassSubject(exam.getClassForSubject());
			updateMarkSubject.setStyle("final");
			markSubjectRepository.save(updateMarkSubject);
		     }
	}
	
	
	
}


@PostMapping("/api/Quiz/StartQuiz")
@ResponseBody
public void StartQuiz(@RequestBody Map<String, Long> requestBody) {
       Long quizExamId = requestBody.get("quizExamId");
       Long studentId = requestBody.get("studentId");
	   QuizExam exam =  quizExamRepository.findById(quizExamId).orElse(null);
	   LocalDateTime now = LocalDateTime.now();
       exam.setStatus("OnProcess"); 
	   exam.setStart_exam_time(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));			   
	   int duration = exam.getQuiz().getDuration();
	   LocalDateTime endDate = now.plusMinutes(duration);
	   exam.setSubmit_exam_time(Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant())); 
	   quizExamRepository.save(exam);

}



@PostMapping("/api/public/Quiz/AutoUpdateQuizExam")
@ResponseBody
public void updateQuiz(@RequestBody Map<String, Long> requestBody,@AuthenticationPrincipal CustomUserDetails currentUser) {
    Long classId = requestBody.get("classId");
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndClassId(currentUser.getUserId(), classId);
	    LocalDateTime now = LocalDateTime.now();
	    for(QuizExam aExam : listQuizExam )
	    {
		    Date endTimeDate = aExam.getSubmit_exam_time();
		    LocalDateTime endTime = endTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		    if(now.isAfter(endTime))
		    {
		    	aExam.setStatus("Submitted");
		 	   quizExamRepository.save(aExam);
		    } 
	    }


}





@Transactional
@PostMapping("/api/Quiz")
public List<QuizQuestion> FindQuizById(@RequestBody Map<String, Long> requestBody) {
    Long quizId = requestBody.get("examIDD");
    System.out.println("quizId from client:" + quizId);
    QuizExam exam = quizExamRepository.getById(quizId);
    
     System.out.print("exam Id"+exam.getId());
  Quiz quiz =quizRepository.getById(exam.getQuiz().getId());
  
  System.out.println("quiz Id:"+quiz.getId());
    List<QuizQuestion>  listqQuestions = quiz.getQuizquestions();
    for(QuizQuestion aQuestion :listqQuestions )
    {
    	System.out.println("question id :" + aQuestion.getId());
    }
	return listqQuestions ;
}


@PostMapping("/api/Quiz/ListExam")
public List<QuizExam> FindExamByStudentId(@RequestBody Map<String, Long> requestBody) {
    Long studentId = requestBody.get("studentId");
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndDate(studentId, date);
	return listQuizExam ;
}



@PostMapping("/api/public/Quiz/ListExam")
public List<QuizExam> FindExamByStudentIdWeb(@RequestBody Map<String, Long> requestBody,@AuthenticationPrincipal CustomUserDetails currentUser) {
     Long classId = requestBody.get("classId");
     System.out.print("quizId:"+ classId);
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndClassId(currentUser.getUserId(), classId);
	return listQuizExam ;
}



@PostMapping("api/Quiz/QuizExamAnswers")
public List<Long> FindSavedAnswer(@RequestBody Map<String, Long> requestBody) {
    Long questionId = requestBody.get("questionId");
    Long quizExamId = requestBody.get("quizExamId");
    
     List<ExamQuestionAnswer>  listExamQuestionAnswers = examQuestionAnswerService.findByQuizExamIdAndQuestionId(quizExamId, questionId);
     List<Long> answerIdList = new ArrayList<>(); ;
     
        for(ExamQuestionAnswer answer:listExamQuestionAnswers )
        {
        	Long idLong = answer.getQuizAnswer().getId();
        	answerIdList.add(idLong);
        }
   
	return answerIdList ;
}



@Transactional
@PostMapping("/api/Quiz/ListAnswers")
public List<QuizAnswer> FindAnswerByQuestionId(@RequestBody Map<String, Long> requestBody) {
    Long questionId = requestBody.get("questionId");

  List<QuizAnswer> answers = quizAnswerService.findAnswerByQuestionId(questionId);
	return answers ;
}



@PostMapping("/listSS")
public List<StudentClass> listSB(@RequestBody Long userid){
    return studentsubservice.findSubjectByStudentId(userid);
}

@GetMapping("/api/public/AllField")
public List<Field> GetAllField() {
    return filedRepository.findAll() ;
}

@GetMapping("/api/public/AllLevel")
public List<SubjectLevel> GetAllLevel() {
    return subjectLevelRepository.findAll() ;
}


/*
 * @PostMapping("/searchSubject") public ResponseEntity<?> search(@RequestBody
 * String value) {
 * 
 * 
 * // Search Value is Subjet Code ? if(value.matches("^\\d+$")) { Long Lvalue =
 * Long.parseLong(value); SubjectDto supject = subService.findbyId(Lvalue);
 * 
 * if(supject!=null) { return ResponseEntity.ok(supject); } else { return
 * ResponseEntity.notFound().build(); } } // Search Value is Name of Subject
 * else { List<Subject> supjects = subService.findBySubjectName(value);
 * if(supjects != null) { return ResponseEntity.ok(supjects); } else { return
 * ResponseEntity.notFound().build(); }
 * 
 * } }
 */






}