package com.aptech.group3.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassSubjectAllDto {
	private String name;
	
	@NotNull(message="vui lòng nhập sĩ số")
	@Min(value=20,message="vui lòng nhập sĩ số")
	private int quantity;
	
	private Long semeter_id;
	
	private ClassStatus status;
	
	private Long subject_id;
	
	 @Valid
	private ClassSubjectBasicDto action;
	 @Valid
	private ClassSubjectBasicDto theory;
	
	
}
