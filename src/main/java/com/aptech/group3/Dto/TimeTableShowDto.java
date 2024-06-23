package com.aptech.group3.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TimeTableShowDto {
private String room;
private int startSlot;
private Long class_id;
private int endSlot;
private String name;
private int weekDay;
private boolean isHoliday;


}
