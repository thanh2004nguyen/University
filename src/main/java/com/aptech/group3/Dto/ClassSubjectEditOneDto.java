package com.aptech.group3.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassSubjectEditOneDto {
	
	private Long  id;

	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private Date dateEnd;

	private int slotStart;
	private int slotEnd;

	private int quantity;
	

	private String type;
	
	private String status;
	
	private int weekDay;
	

	private Long teacher_id;
	
	private Long subject_id;
	

	private Long room_id;
	
	private Long semeter_id;
}
