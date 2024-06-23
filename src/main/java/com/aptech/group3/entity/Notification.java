package com.aptech.group3.entity;

import java.util.Date;

import com.aptech.group3.Dto.NoftifyType;

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

public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private NoftifyType type;
	private Date created_at;

	@ManyToOne()
	@JoinColumn(name = "field_id")
	private Field field;
	
	private boolean sentEmail;
	
	private boolean sentMobile;

	@ManyToOne()
	@JoinColumn(name = "class_id")
	private ClassForSubject classSbject;

	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User creator;
	
	@ManyToOne()
	@JoinColumn(name = "semester_id")
	private Semeter semester;

}
