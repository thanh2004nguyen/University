package com.aptech.group3.Dto;

import java.time.LocalDate;
import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizExamCreateDto {

	    private Long quizId;
	    private Long classId;
	    private LocalDate startDate; 
	    private LocalDate endDate;
	   
}
