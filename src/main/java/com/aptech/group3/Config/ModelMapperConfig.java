package com.aptech.group3.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.QuizQuestionCreateDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.QuizQuestion;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        
		/*
		 * modelMapper.createTypeMap(ClassForSubject.class, ClassForSubjectDto.class)
		 * .addMapping(src -> src.getUser(), ClassForSubjectDto::setTeacher);
		 */
        
        // Define mappings
		/*
		 * modelMapper.createTypeMap(QuizQuestion.class, QuizQuestionCreateDto.class)
		 * .addMapping(src -> src.getQuiz().getId(), QuizQuestionCreateDto::setQuizId)
		 * .addMapping(src -> src.getQuizAnswers().stream() .filter(quizAnswer ->
		 * quizAnswer.getIsTrue() == 1) // Filter correct answers .map(quizAnswer ->
		 * quizAnswer.getContent()) .collect(java.util.stream.Collectors.toList()),
		 * QuizQuestionCreateDto::setCorrectAnswers) .addMapping(src ->
		 * src.getQuizAnswers().stream() .filter(quizAnswer -> quizAnswer.getIsTrue() ==
		 * 0) // Filter incorrect answers .map(quizAnswer -> quizAnswer.getContent())
		 * .collect(java.util.stream.Collectors.toList()),
		 * QuizQuestionCreateDto::setIncorrectAnswers);
		 */
        
        
        return modelMapper;
    }
    
    
    
}