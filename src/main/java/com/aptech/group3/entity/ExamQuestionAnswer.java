package com.aptech.group3.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name = "exam_question_answer")
public class ExamQuestionAnswer {
    @Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	@JoinColumn(name="exam_id" , referencedColumnName="id")
	 private QuizExam quizExam;

	@ManyToOne
	@JoinColumn(name="question_id" , referencedColumnName="id")
	 private QuizQuestion quizQuestion;
	@ManyToOne
	@JoinColumn(name="answer_id" , referencedColumnName="id")
	 private QuizAnswer quizAnswer;
}
