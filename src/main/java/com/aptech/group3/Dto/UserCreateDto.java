package com.aptech.group3.Dto;

import java.util.List;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;




@Data
public class UserCreateDto    {

	private Long id;
    @NotEmpty(message="{admin.user.email.error}")
	@Email(message="please input follow form of email")
	private String email;
    private String code;
	private String password;

	@NotEmpty(message="{admin.user.name.error}")
	private String name;


	@NotEmpty(message="{admin.user.phone.error}")
	private String phone;
	
	private String infomation;
	private String role;
	private String address;
	private String avatar;
	private List<Long> fields;
	
}