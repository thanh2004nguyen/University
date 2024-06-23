package com.aptech.group3.entity;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name="semeter")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Semeter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int name;
	private int year;
	private Date day_start;
	private Date day_end;
	
	private Date startRegisDate;
	private Date closeRegisDate;
	

}