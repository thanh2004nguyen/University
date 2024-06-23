package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkDetailDto {
private MarkApiDto markTotal;
private float normalMark; 
private float middleMark;
private float fiinalMark;
private String result;
}
