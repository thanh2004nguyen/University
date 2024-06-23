package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.ClassSubject;
import com.aptech.group3.entity.ClassForSubject;

import jakarta.transaction.Transactional;

public interface ClassForSubjectRepository extends JpaRepository<ClassForSubject, Long> {
	
	List<ClassForSubject> findByNameContainingIgnoreCase(String className);
	
	@Query(" SELECT s FROM class_subject s WHERE s.subject.id = :subjectId")
	List<ClassForSubject> lissClassBySubjectId(Long subjectId);
	
	
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND " + " s.subject.field.id = :fieldId "
			+ " AND (:subjectId IS NULL OR s.subject.id = :subjectId ) AND (:status IS NULL OR s.status =:status) ")
	Page<ClassForSubject> findByFieldIdAndSubjectIdAndStatus(Long semesterId, Long fieldId, Long subjectId,ClassSubject status, Pageable pageable);
	
	
	
	
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND " + " s.subject.field.id = :fieldId "
			+ " AND (:subjectId IS NULL OR s.subject.id = :subjectId ) AND (:status IS NULL OR s.status =:status)")
	List<ClassForSubject> getListByCondition(Long semesterId, Long fieldId, Long subjectId,ClassSubject status);
	
	@Transactional
	@Modifying
	@Query("UPDATE class_subject s SET s.status =:status Where s.id IN :listId")
	void updateStatue(ClassSubject status , List<Long> listId);
	
	// new
	
	List<ClassForSubject> findByteacherIdAndSemeterId(Long teacherID, Long semesterId);
	@Query("SELECT c FROM class_subject c JOIN LessonSubject l ON c.id = l.classSubject.id WHERE DATE(l.day) = DATE(:today) AND c.teacher.id = :userId")
	List<ClassForSubject> findAllByDateTodayAndUserId(@Param("today") Date today, @Param("userId") Long userId);
	
	@Query("SELECT c FROM class_subject c JOIN LessonSubject l ON c.id = l.classSubject.id"
			+ " WHERE c.teacher.id= :teacherId AND c.semeter.id= :semesterId  "
			+ " AND :dateStart >= c.dateStart  AND :dateEnd<= c.dateEnd")
	List<ClassForSubject> getBystartEndAndTeacherId(Long teacherId,Long semesterId, Date dateStart, Date dateEnd );
	



	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId")
	List<ClassForSubject> lissClassBySemesterId(Long semesterId);

	List<ClassForSubject> findBySubject_Id(Long subjectId);

	List<ClassForSubject> findBySemeter_Year(int year);

	List<ClassForSubject> findByName(String name);

	@Query("SELECT c FROM class_subject c " + "WHERE c.subject.id = :subjectId "
			+ "AND :date BETWEEN c.semeter.startRegisDate AND c.semeter.closeRegisDate")
	List<ClassForSubject> findBySubjectIdAndDateBetweenRegistration(@Param("subjectId") Long subjectId,
			@Param("date") Date date);
	
	
	@Query("SELECT c FROM class_subject c " 
			+ "WHERE :date BETWEEN c.semeter.startRegisDate AND c.semeter.closeRegisDate")
	List<ClassForSubject> findClasWhereDateBetweenRegistration(@Param("date") Date date);
	
	

	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id=:semesterId AND s.subject.field.id=:fieldId ")
	List<ClassForSubject> findBySemeter_IdAndSubject_field_Id(Long semesterId, Long fieldId);

	List<ClassForSubject> findBySubjectId(Long id);

	



	public ClassForSubject findById(int id);

	// HIEN
	public List<ClassForSubject> findByTeacherId(Long id);

	// thanh thêm để lấy học kỳ theo lớp có học kỳ = nhau
	public List<ClassForSubject> findAll();

	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND " + " s.subject.field.id = :fieldId ")
	public List<ClassForSubject> findBySemeterIdAnd(Long semesterId, Long fieldId);
}
