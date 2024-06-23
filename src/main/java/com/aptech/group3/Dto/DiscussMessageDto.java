package com.aptech.group3.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiscussMessageDto {


    private Long id;
	private String text;
	private Date createAt;
	
	private String sender_name;
	private Long sender_id;
	private boolean isTeacher;

}
