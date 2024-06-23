package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkApiDto {
 private String className;
 private String teacherName;
 private float finalMark;
 private String subjectName;
 private ClassSubject classStatus;
 private Long ClassId;
 

}
