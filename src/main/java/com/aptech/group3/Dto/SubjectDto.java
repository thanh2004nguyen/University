package com.aptech.group3.Dto;

import java.util.List;
import java.util.Set;

import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;

import lombok.Data;

@Data
public class SubjectDto {
	

    private Long id;
    private String name;
    private int credit;
    private SubjectLevel subjectlevel;
    private Field field;
    private ClassType type;
    private Integer creditAction;

    private List<String> optionalRequiredSuject;
	private List<String> passedSubjects;
	
	
	private boolean hidden= false;
}
