package com.aptech.group3.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkSubject {
         @Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
   	@ManyToOne
   	@JoinColumn(name="student_id" , referencedColumnName="id")
   	private User user;
   	
   	
	@ManyToOne
	@JoinColumn(name="class_id" , referencedColumnName="id")
	private ClassForSubject classSubject;
	
	@ManyToOne
	@JoinColumn(name="subject_id" , referencedColumnName="id")
	private Subject subject;
   	
   	private float mark;
   	private String style;
}
