package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AttendanceCreateDto {
private Long studentId;
private String status;
}
