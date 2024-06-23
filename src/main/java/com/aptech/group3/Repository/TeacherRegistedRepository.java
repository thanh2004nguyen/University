package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.TeacherRegisted;

public interface TeacherRegistedRepository  extends JpaRepository<TeacherRegisted, Long> {
	@Query("SELECT t FROM teacher_registed t WHERE t.class_registed.id= :classId ")
	public  TeacherRegisted findByClass_registedId(Long classId);
	
	/* new */

	
	public List<TeacherRegisted> findByTeacherIdAndSemesterId(Long id,Long semesterId);
	
	@Query("SELECT t FROM teacher_registed t WHERE t.teacher.id= :teacherId AND "
			+ " t.class_registed.id= :classId")
	public TeacherRegisted checkTeacher(Long teacherId,Long classId);

}
