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
@Entity(name="teacher_registed")
public class TeacherRegisted {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	@ManyToOne
	@JoinColumn(name="class_id" , referencedColumnName="id")
	private ClassForSubject class_registed;
	@ManyToOne
	@JoinColumn(name="teacher_id" , referencedColumnName="id")
	private User teacher;
	@ManyToOne
	@JoinColumn(name="semester_id" , referencedColumnName="id")
	Semeter semester;
}
