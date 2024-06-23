package com.aptech.group3.entity;

import java.util.Date;

import jakarta.persistence.Column;
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

public class DiscussMessage {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String text;
	@Column(name="created_at")
	private Date createAt;
	
	@ManyToOne()
	@JoinColumn(name = "student_id")
	private User student;
	
	@ManyToOne()
	@JoinColumn(name = "discuss_id")
	private DiscussRoom discuss;

	
}
