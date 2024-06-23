package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.TimeTableApiDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.StudentClassApiDto;
import com.aptech.group3.entity.User;

@Service
public interface StudentClassService {
	
	  public List<StudentClass> getStudentClasses(Long studentId) ;
	
	void updateStatus(List<Long> ids, int status);
	List<StudentClass> findStudentClassesByUserName(String code);
	
	//vuong
	
	public void  updateStatusToPaid() ;
	
	List<Long> getListStudentRegistered(Long studentid , List<Long> listId);
	

	
	//thanh thêm1
	public List<ClassForSubject> getTodayClassStudent(Long studentId, Long SemesterId) ;

	public List<ClassForSubject> getListClassStudent(Long studentId, Long SemesterId);
	
	
	

	public void DeleteByClassIdAndStatus(Long classId, ClassStatus status);
	public List<StudentClass> getListByClassIdAndStatus(Long classId, ClassStatus status);
	public void updateManyStudentStatusByClassId(Long classId,ClassStatus status);
	// new



	public List<StudentClassApiDto> getCurrentClassList(Long studentId, Long SemesterId);

	public List<TimeTableApiDto> getTimtableApiDto(Long studentId, Date day);

	public void RegisterClassMobile(Long classId, Long userId);

	public List<StudentClass> getListStudentByClassId(Long classId);

	public List<User> getStudentsByClassAndSubject(Long classId);

	public List<AttendanceDto> getListStudentInClass(Long classId);

	public List<AttendanceDto> getListStudentByCode(String code, Long classId);

	public List<TimeTableShowDto> getCurrentTimeTable(Long studentId, Date dateStart, Date dateEnd, Long semesterId);

	public List<StudentClass> findSubjectByStudentId(Long studentId);

	public List<StudentClass> findEarliestByStatus(ClassStatus waitinglist);

	public void RegisterClass(ClassForSubjectDto dto, Long userId);

	public boolean CheckStuentInClass(Long stuentId, Long ClassId);

	public List<StudentClass> findByClassForSubjectId(Long classId);

	public void updateItemsStatusToPayment(List<Long> idList);

	public List<StudentClass> findByStudentIdAndStatus(Long studentId, ClassStatus status);
}
