package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;

import jakarta.transaction.Transactional;



public interface StudentClassRepository extends JpaRepository<StudentClass ,Long> {
	
	
	List<StudentClass> findByStudentIdAndStatusIn(Long studentId, List<ClassStatus> statuses);
	
	
	List<StudentClass> findAllById(Iterable<Long> ids);
	@Query("SELECT sc FROM StudentClass sc WHERE sc.student.code = :code AND sc.status = 2 ")
    List<StudentClass> findByStudentName(@Param("code") String code);
	List<StudentClass> findByStatus(ClassStatus status);
	

	@Query("SELECT o.id FROM StudentClass o WHERE o.student.id =:studentid and o.classforSubject.id in :listId")
	List<Long> getListStudentRegistered(Long studentid , List<Long> listId);

	@Query("SELECT s FROM StudentClass s WHERE s.classforSubject.id = :classId AND (:status IS NULL  OR s.status = :status)" )
	List<StudentClass> searchbyClassIdAndStatus(Long classId, ClassStatus status );
	
	
	@Modifying
	@Transactional
	@Query("UPDATE StudentClass s SET s.status= :status WHERE s.status= :st2")
	void updateSatustoStatus( ClassStatus status,ClassStatus st2);
	
	@Modifying
	@Transactional
	@Query("DELETE  FROM StudentClass s WHERE s.id IN :listId")
	void deleteManyStudent(List<Long> listId);
	
	@Modifying
	@Transactional
	@Query("UPDATE StudentClass s SET s.status = :status  WHERE s.classforSubject.id = :classId")
	void updateStatusManyStudent(ClassStatus status,Long classId );
	
	// new 
	@Query("SELECT s.classforSubject FROM StudentClass s JOIN s.classforSubject cs JOIN cs.lessons l WHERE s.student.id = :studentId AND DATE(l.day) = DATE(:today) AND s.classforSubject.semeter.id = :semesterId")
	List<ClassForSubject> getTodayClassSubject( @Param("today") Date today, Long studentId,  Long semesterId);
	
	
	@Query("SELECT s.classforSubject FROM StudentClass s WHERE s.student.id = :studentId"
			+ " AND s.classforSubject.semeter.id = :semesterId")
	List<ClassForSubject>getListClassSubject(Long studentId,Long semesterId );
	
	@Query( "SELECT s FROM StudentClass s WHERE s.student.id= :studentId AND s.classforSubject.semeter.id= :semesterId"
			+ " AND s.status = :status ")
	 List<StudentClass>  getCurrentLIstClass( Long studentId, Long semesterId,ClassStatus status );
	
	
 
	@Query("SELECT s FROM StudentClass s WHERE s.student.id = :studentId AND "
	        + "DATE(s.classforSubject.dateStart) <= DATE(:day) AND DATE(s.classforSubject.dateEnd) >= DATE(:day)"
	        + "AND s.classforSubject.weekDay = :weekday")
	List<StudentClass> getScheduleByDay(Long studentId, Date day, int weekday);
	
	
	//thanh
	@Query("SELECT o FROM StudentClass o WHERE o.classforSubject.id = :classId")
    List<StudentClass> getStudentByClassId(Long classId);
	
	List<StudentClass> findByClassforSubject_Id(Long classId);
	
	

    
    
	@Query("SELECT o FROM StudentClass o WHERE o.student.code LIKE %:code% AND o.classforSubject.id = :classId")
    List<StudentClass> getStudentByCodeAndClassId(String code, Long classId);
	
	

	@Query("SELECT o FROM StudentClass o WHERE o.student.id = :studentId AND "
			+ " o.classforSubject.semeter.id= :semesterId "
			+ " AND DATE(:dateEnd)<= DATE(o.classforSubject.dateEnd)")
	
	
	List<StudentClass> getcalendar(Long studentId,  Date dateEnd, Long semesterId);
	
	
	@Query("SELECT DISTINCT o FROM StudentClass o JOIN o.classforSubject cs JOIN cs.lessons l WHERE o.student.id = :studentId AND "
	        + " cs.id = :classId "
	        + " AND DATE(l.day) = DATE(:day)")
	public StudentClass getInfoByStudentIdAndDayAndClassId(Long studentId, Long classId, Date day);
	
	
	
	//new

		List<StudentClass> findByStudentIdAndStatus(Long studentId, ClassStatus status);
		
      List<StudentClass> findByStudentId(Long studentId);
      List<StudentClass> findByClassforSubjectId(Long classId);
      
      List<ClassForSubject> findClassForSubjectsByStudentId(Long studentId);

       List<StudentClass> findByStudent_Id(Long studentId);
      
      
       StudentClass findByStudent_IdAndClassforSubject_Id(Long sId ,Long cId);
       
	
       
      
      // Assuming createDate is a field of type Date in StudentClass
      @Query("SELECT sc FROM StudentClass sc WHERE sc.status = :status ORDER BY sc.createDate ASC")
      List<StudentClass> findEarliestByStatus(@Param("status") ClassStatus status);
}
