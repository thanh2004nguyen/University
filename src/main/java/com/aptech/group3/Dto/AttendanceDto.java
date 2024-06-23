package com.aptech.group3.Dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AttendanceDto {
private Long student_id ;
private String student_name ;
private String student_code;
private String status;
}
