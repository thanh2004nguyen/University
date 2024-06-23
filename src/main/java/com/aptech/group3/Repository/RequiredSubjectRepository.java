package com.aptech.group3.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.Subject;


public interface RequiredSubjectRepository extends JpaRepository<RequiredSubject,Long>{
	Optional<RequiredSubject> findById(Long id);	
	RequiredSubject findByStatus(String status);
	
	 List<RequiredSubject> findBySubjectId(Long subjectId);
	 
	 
	 
	 
	
	
	
}
