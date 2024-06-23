package com.aptech.group3.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentClassApiDto {
	  private  Long id ;
	  private String className;
	 private String subjectName;
	 private String teacherName;
	  
}
