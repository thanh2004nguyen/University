package com.aptech.group3.Dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateProfileDto {
		private Long id;
	    @NotEmpty(message="{admin.user.email.error}")
		private String email;
		@NotEmpty(message="{admin.user.name.error}")
		private String name;
		@NotEmpty(message="{admin.user.phone.error}")
		private String phone;
		private String infomation;
		private String address;
		private String avatar;
		private String password;
		private List<Long> fields;
}
