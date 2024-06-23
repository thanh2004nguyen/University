package com.aptech.group3.Dto;

import com.aptech.group3.entity.User;

import lombok.Data;

@Data
public class MarkSubjectDto {

	 private Long id;
   	 private User student;
   	 private User subject;
}
