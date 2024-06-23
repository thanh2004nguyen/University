package com.aptech.group3.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableDto {
	int week;
	Date start_day;
	Date end_day;
}
