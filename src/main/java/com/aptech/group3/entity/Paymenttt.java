package com.aptech.group3.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter

public class Paymenttt {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="student_id" , referencedColumnName="id")
	 private User student;
	
	
	@ManyToOne
	@JoinColumn(name="class_id" , referencedColumnName="id")
	 private ClassForSubject classforSubject;
	private int cash;
	private String payments;
	private Date date;
	
}
