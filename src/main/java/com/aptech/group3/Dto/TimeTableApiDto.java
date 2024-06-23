package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableApiDto {

	private String room;
	private int startSlot;
	private int endSlot;
	private String className;
	private int weekDay;
	private String status;
	private String subjectName;
}
