package com.aptech.group3.Dto;

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
public class QuizCreateDto {

	@NotNull(message = "Must Input Name Of Exam")
	@NotEmpty(message = "Must Input Name Of Exam")
	  private String name;
	  
	 @Min(value=1,message="Mark Must greater than 0")
	  private int duration;
	  
	
	 @NotNull(message="plese select Type")
	  @Min(value=1,message="plese select Type")
	  private String type;
	  
	  @NotNull(message="Please Input Mark")
	  @Min(value=1,message="Mark Must greater than 0")
	  private float  totalMark;
	  
	
	   private Date   createDate;
	  
	
        private Long   subject_id;
        
        @NotNull(message = "A subject must be selected.")
        @NotEmpty(message = "A subject must be selected.")
	   private String subjectName;
	   
}
