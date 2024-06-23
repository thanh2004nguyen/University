package com.aptech.group3.entity;

import java.util.Date;
import java.util.List;

import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.ClassSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity(name = "class_subject")
public class ClassForSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Date dateStart;
	private Date dateEnd;
	private int slotStart;
	private int slotEnd;

	private String description;
	private ClassSubject status;
	private int weekDay;
	private String type;

	private int quantity;
	private int currentQuantity;
	private int minQuantity;
	

	
	@JsonIgnore
	 @OneToMany(mappedBy="classSubject")
	    private List<LessonSubject> lessons;

	@ManyToOne
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	private User teacher;

	@ManyToOne
	@JoinColumn(name = "subject_id", referencedColumnName = "id")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;

	@ManyToOne
	@JoinColumn(name = "semeter_id", referencedColumnName = "id")
	private Semeter semeter;

}
