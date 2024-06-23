package com.aptech.group3.Dto;



import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SemeterDto {
	private Long id;
    private int name;
	@Min(2024)
	private int year;
	@NotNull
	@Min(2024)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date daystart;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dayend;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startRegisDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closeRegisDate;
}