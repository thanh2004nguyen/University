package com.aptech.group3.Dto;

import java.util.List;

import com.aptech.group3.entity.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshDto{
	private String refeshToken;
    private String accessToken;
	
}