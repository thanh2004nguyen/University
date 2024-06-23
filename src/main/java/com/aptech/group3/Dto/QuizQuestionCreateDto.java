package com.aptech.group3.Dto;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class QuizQuestionCreateDto {


	  @NotNull
	  @NotEmpty
	  private String content;
	  
	     @NotNull(message="Please Input Mark")
		 @Min(value=1,message="Mark Must greater than 0")
	  private float mark;
	  
	  
	  private String type;
	  
	  
      private Long quizId;	 	 
      
 
      private List<String> correctAnswers;

      private List<String> incorrectAnswers;
}
