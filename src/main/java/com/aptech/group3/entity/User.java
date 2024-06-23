package com.aptech.group3.entity;

import java.util.List;

import com.aptech.group3.Dto.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String code;
	private String name;
	private String password;
	private String phone;
	private String infomation;
	private String role;
	private String address;
	private String avatar;
	private String mobileCode;
	private String resetPasswordToken;
	private UserStatus status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_field", 
	    inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id")
	)
	@JsonIgnore
	  private List<Field> fields;
	
}