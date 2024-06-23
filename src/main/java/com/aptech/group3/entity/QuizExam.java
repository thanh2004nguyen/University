package com.aptech.group3.entity;


import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity

public class QuizExam {
	    @Id
     	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	    
	   private Date startFromDate;
	   private Date closeDate;
		  
		  
	  private Date start_exam_time;
	  private Date submit_exam_time;
	  private float totalMark;
	  
	  private String status;
	  
	    @ManyToOne
	    @JoinColumn(name = "user_id",referencedColumnName="id")
	    private User student;
	    
	    @ManyToOne
	    @JoinColumn(name = "quiz_id",referencedColumnName="id")
	    private Quiz quiz;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "class_id",referencedColumnName="id")
	    private ClassForSubject classForSubject;


	   
}