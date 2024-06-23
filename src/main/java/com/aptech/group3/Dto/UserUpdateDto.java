package com.aptech.group3.Dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Phone cannot be empty")
    private String phone;
    private String address;
    private String infomation;
    private String role;
    private String avatar; 
    private UserStatus status;
    private List<Long> fieldIds;
}
