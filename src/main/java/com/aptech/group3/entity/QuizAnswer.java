package com.aptech.group3.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class QuizAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String content;
	  private int isTrue;
	  
	  
	  @ManyToOne
	  @JoinColumn(name="quiz_question_id")
      private QuizQuestion quizQuestion;

}
