package com.aptech.group3.Dto;

import com.aptech.group3.entity.Subject;

import lombok.Data;

@Data

public class RequiredSubjectDto {
	private Long id;
	private Long subjectId;
	private Long requiredsubjectId;
	private String status;
}
