package com.aptech.group3.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.MarkApiDto;
import com.aptech.group3.Dto.MarkDetailDto;
import com.aptech.group3.Dto.MarkSubjectCreateDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.QuizExam;

import jakarta.servlet.ServletOutputStream;


@Service
public interface MarkSubjectService {
	

	
	
	public List<MarkApiDto> getListMarkDto(Long studentId);
	public MarkDetailDto getMarkDetailDto( Long studentId,Long ClassId);
	public List<MarkSubject> getMarksByStudentId(Long studentId);
	


	 public List<ClassForSubject> getClassForSubjectBySubjectId(Long subjectId);
	 public MarkSubject getMarkSubjectById(Long id);
	 public List<MarkSubject> getListMarkSubjectByClassId(Long classId);
	 public List<MarkSubject> getListMarkSubjectByStudentIdAndClassId(Long userId, Long classId);
	 public void exportToExcel(List<MarkSubject> marks, ServletOutputStream outputStream);
	 public void insertMarks(List<MarkSubjectCreateDto> markSubjectCreateDtoList) ;



	public Map<Long, Integer> getStudentMarksByClassAndMarkType(Long classId, String markType);



	public void updateStudentMark(Long studentId, String markType, Float updatedMark);



	public List<MarkSubject> getListMarkSubjectByClassIdAndStyle(Long classId, String style);
}
