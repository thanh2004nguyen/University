package com.aptech.group3.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonApiDto {
	private int lesson;
	private Date day;
	private String status;
}
