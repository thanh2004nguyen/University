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
public class ClassSubjectCreateDto {

	private String name;
	
	@NotNull(message="{label.error}")
	@NotEmpty(message="{label.error}")
	private String date_start;
	
	private String date_end;
	@NotNull(message="plese select slot start")
	@Min(value=1,message="plese select slot end")
	private int slotStart;
	private int slotEnd;
	@NotNull(message="vui lòng nhập sĩ số")
	@Min(value=2,message="min quantity is 2")
	private int quantity;
	
	@NotEmpty(message="please select type of class")
	private String type;
	
	private String status="waiting";
	
	private int weekDay;
	
	@NotNull(message="{label.error}")
	@Min(value=1,message="please select teacher")
	private Long teacher_id;
	
	private Long subject_id;
	
	@NotNull(message="{label.error}")
	private Long room_id;
	
	private Long semeter_id;
	
	
	
}
