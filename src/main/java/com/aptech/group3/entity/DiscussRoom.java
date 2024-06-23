package com.aptech.group3.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String topic;
	private Date expried;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id")
	private User teacher;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "class_id")
	private ClassForSubject owner;
	
	
	@OneToMany(mappedBy="discuss") 
    private List<DiscussMessage> messages;

}
