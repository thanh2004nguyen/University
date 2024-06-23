package com.aptech.group3.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectBasicDto {
	@NotNull(message="{label.error}")
	@NotEmpty(message="{label.error}")
	private String date_start;
	
	private String date_end;
	
	@NotNull(message="plese select slot start")
	@Min(value=1,message="plese select slot end")
	private int slotStart;
	private int slotEnd;
	
	private int weekDay;
	@NotNull(message="{label.error}")
	private Long teacher_id;
	@NotNull(message="{label.error}")
	private Long room_id;
	@NotNull(message="plese select slot start")
	@NotEmpty(message="please select type of class")
	private String type;
}
