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
public class LessonSubject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	private int lesson;
	private Date day;
	private String type;
	@ManyToOne
	@JoinColumn(name="class_id" , referencedColumnName="id")
	private ClassForSubject classSubject;
	

}
