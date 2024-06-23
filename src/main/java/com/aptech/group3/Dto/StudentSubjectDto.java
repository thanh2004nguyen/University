package com.aptech.group3.Dto;



import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;

import lombok.Data;

@Data
public class StudentSubjectDto {
     private Long id ;
	 private User student;
	 private Subject subject;
	
    
}
