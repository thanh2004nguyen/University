package com.aptech.group3.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.SubjectLevel;






public interface SubjectLevelRepository extends JpaRepository<SubjectLevel, Long> {
	
	Optional<SubjectLevel> findById(Long id);
	SubjectLevel findByName(String name);
	
}
