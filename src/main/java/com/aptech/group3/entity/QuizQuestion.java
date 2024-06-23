package com.aptech.group3.entity;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class QuizQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	

	  private String content;
	  private float mark;
	  private String type;
	  
	  @ManyToOne
	  @JoinColumn(name="quiz_id")
      private Quiz quiz;
	  
	  @OneToMany(mappedBy="quizQuestion", cascade = CascadeType.ALL)
	  @JsonIgnore
	  private List<QuizAnswer> quizAnswers; 
	 
}
