package com.aptech.group3.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class SubjectLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String name;
	  @OneToMany(mappedBy="subjectlevel" , cascade = CascadeType.ALL)
	  @JsonIgnore
	  private List<Subject> rooms;

}
