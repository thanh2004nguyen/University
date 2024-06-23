package com.aptech.group3.entity;


import java.util.List;
import java.util.Set;

import com.aptech.group3.Dto.ClassType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String name; // LomBok khong duoc viet hoa
	  private int credit;
	  private ClassType type;
	  private Integer creditAction;
	  
	  private boolean hidden;
	  
	  @ManyToOne
	  @JoinColumn(name="subjectlevel_id")
	  private SubjectLevel subjectlevel;
	  
	  @ManyToOne
	  @JoinColumn(name="field_id")
	  private Field field;
	  
	  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	  private Set<RequiredSubject> requiredSubjects;
  
}
