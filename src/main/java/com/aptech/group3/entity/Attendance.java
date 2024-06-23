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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String status ;
	private Date day;
	
	@ManyToOne() 
	@JoinColumn(name = "student_id")
	private User student;
	
	@ManyToOne()
	@JoinColumn(name = "lesson_id")
	private LessonSubject lesson;
	

}
