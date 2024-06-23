package com.aptech.group3.entity;



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
@Entity(name = "quiz_class")
public class QuizClass {
    @Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	@JoinColumn(name="classforsubject_id" , referencedColumnName="id")
	 private ClassForSubject classForSubject;

	@ManyToOne
	@JoinColumn(name="quiz_id" , referencedColumnName="id")
	 private Quiz quiz;

}
