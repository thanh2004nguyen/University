package com.aptech.group3.Dto;


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
public class HolidayCreateDto {
	
	@NotNull(message= "{holiday.create.date.error}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;

@NotNull(message="{holiday.end.date.error}")
@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateEnd;

@NotEmpty(message= "{label.name.error}")
	private String name;

	private int year;
	private Long classIdHoliday ;
	
	@NotNull(message="{holiday.type.error}")
	private String type;
}
