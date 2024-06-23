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
public class DiscussRoomDto {
	@NotEmpty(message="{label.name.error}")
	@NotNull(message="{label.name.error}")
	private String name;
	@NotEmpty(message="{discuss.topic.error}")     
	@NotNull(message="{discuss.topic.error}")
	private String topic;
	@NotNull(message="{discuss.type.error}")
	private String type;
	
	@NotNull(message="{discuss.expired.error}")
	@Min( value=1,message="{discuss.expired.error}")
	private int expried_date;

	private Long teacher_id;

	@NotNull(message="{discuss.class.error}")
	private Long class_id;

}
