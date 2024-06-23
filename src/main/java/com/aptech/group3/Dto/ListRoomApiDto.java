package com.aptech.group3.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRoomApiDto {
	private Long id;

	private String name;

	private String topic;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expired_date;

}
