package com.aptech.group3.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
public class RequiredSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// status : MUSTPASS cần pass môn A mới học đc môn B
	// status :  Optional-Pass chỉ cần Pass ít nhất 1 môn trong list môn học để học đc môn B
	private String status;
	
	@ManyToOne
	@JoinColumn(name="subject_id" )
	 private Subject subject;

	@ManyToOne
	@JoinColumn(name="required_subject_id")
	 private Subject requiredsubject;

}
