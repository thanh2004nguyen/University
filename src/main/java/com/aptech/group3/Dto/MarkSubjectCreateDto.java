package com.aptech.group3.Dto;

import com.aptech.group3.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkSubjectCreateDto {
	private Long classId;
    private Long studentId;
    private Long subjectId;
    @NotNull(message = "Mark must not be null")
    private float mark;
    @NotBlank(message = "Style must not be blank")
    private String style;
    
}
