package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDto {
	private UserApiDto user;
	private RefreshDto token;
	
}
