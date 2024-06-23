package com.aptech.group3.Dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HolidayEditDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message= "{label.name.error}")
	private String name;

	@NotNull(message= "{holiday.create.date.error}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;
	
	@NotNull(message= "{holiday.create.date.error}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateEnd;

	private int year;
	
	private Long classId;

	@NotNull(message="{holiday.type.error}")
	private String type;
}
