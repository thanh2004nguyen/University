package com.aptech.group3.Dto;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailAttendClassDto {

	private String className;
	private String SubjectName;
	private List<LessonApiDto> lessons;
	
}
