package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.LessonSubject;

import jakarta.transaction.Transactional;

public interface LessonSubjectRepository extends JpaRepository<LessonSubject,Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM LessonSubject l WHERE l.classSubject.id= :classId")
	void deleteLessonByClassId(Long classId);
	
	@Query("SELECT l FROM LessonSubject l WHERE l.classSubject.id= :classId AND DATE(l.day)= DATE(:day)")
	List<LessonSubject> getLessonByDay( Long classId, Date day);
	
	List<LessonSubject>findByClassSubject_Id(Long subjectId);
	
	List<LessonSubject> findByClassSubjectId(Long classSubjectId);
	
	@Query("SELECT l FROM LessonSubject l WHERE DATE(:checkDay) = Date(l.day) AND l.classSubject.id= :classId AND l.type LIKE 'holiday'" )
	List<LessonSubject> checkIsHoliday(Date checkDay,Long classId);
	
	
	 

}
