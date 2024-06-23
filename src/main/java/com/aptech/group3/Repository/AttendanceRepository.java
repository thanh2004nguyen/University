package com.aptech.group3.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

	@Query("SELECT a FROM Attendance a WHERE a.student.id= :studentId AND "
			+ " DATE(a.day) = DATE(:date) AND a.lesson.classSubject.id=:classId")
	public Attendance getDetailByDay( Long studentId, Date date, Long classId);
	
	@Query("SELECT a FROM Attendance a WHERE a.lesson.id = :lessonId "
			+ " AND DATE(a.day) = DATE(:date) AND a.student.id= :studentId")
	public Attendance findByStudent_IdAndLesson_IdAndDay(Long lessonId,Long studentId, Date date);
	
	
	@Query("SELECT a FROM Attendance a WHERE a.lesson.id = :lessonId AND DATE(a.day) = DATE(:date)")
    List<Attendance> findByClassSubject_IdAndDay( Long lessonId,  Date date);
	
	@Query("SELECT DISTINCT a FROM Attendance a WHERE a.lesson.classSubject.id = :classID "
			+ " AND DATE(a.day) = DATE(:date) AND a.student.id= :studentId")
	Attendance findDetailAttendance(Long studentId,Long classID, Date date);
	
	@Query("SELECT  a FROM Attendance a WHERE a.lesson.classSubject.id = :classID "
			+ "  AND a.student.id= :studentId")
	List<Attendance> findByClassAndStudent(Long classID,Long studentId);
	
}
