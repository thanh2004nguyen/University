package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApiDto {
	Long user_id;
	private String name;
	private String avatar;
	private String role;
	private String code;
}
