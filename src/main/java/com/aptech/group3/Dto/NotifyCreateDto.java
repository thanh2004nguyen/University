package com.aptech.group3.Dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotifyCreateDto {
	@NotNull(message="{notify.error.title.null}")
	@NotEmpty(message="{notify.error.title.empty}")
	private String title;
	
	
	@NotEmpty(message="{notify.error.title.empty}")
	private List<String> typeSent;
	
	@NotNull(message="{notify.error.content.null}")
	@NotEmpty(message="{notify.error.content.empty}")
	private String content;
	
	@NotNull(message="{notify.error.type.null}")
	private NoftifyType type;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created_at;
	
	private Long field_id;
	
	private Long class_id;

}
