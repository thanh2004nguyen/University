package com.aptech.group3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="teacher_subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
	
	@ManyToOne
	@JoinColumn(name="teacher_id" , referencedColumnName="id")
	 private User teacher;
	@ManyToOne
	@JoinColumn(name="subject_id" , referencedColumnName="id")
	 private Subject subject;
}
