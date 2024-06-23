package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.MarkSubjectDto;
import com.aptech.group3.entity.MarkSubject;


public interface MarkSubjectRepository extends JpaRepository<MarkSubject,Long> {
	List<MarkSubject> findByClassSubject_IdAndStyle(Long classId, String style);
	
	
		//thêm mới ở trên  
	    public List<MarkSubject> findByUserId(Long userId);
	    
	    @Query("SELECT ms FROM MarkSubject ms JOIN class_subject cs ON ms.subject.id = cs.subject.id WHERE cs.id = :classId")
	    List<MarkSubject> findByClassId(@Param("classId") Long classId);

	    @Query("SELECT m FROM MarkSubject m WHERE m.user.id=:userId AND m.classSubject.id=:classForSubjectId ")
	    List<MarkSubject> findByUserIdAndClassForSubjectId( Long userId, Long classForSubjectId);

		MarkSubject findByUserIdAndStyle(Long studentId, String markType);
		
	}

