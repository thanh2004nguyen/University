package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;

public interface SubjectService {
	

	
	
	public List<SubjectDto> findByStudentMoNeedRequiredSubjectCondition(Long field);
	
	public List<Subject> findPassesSubject(User student);
	 public Subject findByName(String name);
	 public List<SubjectLevel> listSubjectLevel();
	 public List<Subject> findBySubjectName(String name);
	 public Optional<Subject> findbyId(Long id);
	 
	 public List<Subject> findByLevel(Long levelId);
	 public List<Subject> searchSubject(String name, Integer fieldId, Integer levelId);
	 public List<Subject> findAll();
	 public int getCredit(int id);
	 public List<Subject> getByField(Long id);
	 public Subject saveSubject(Subject sub);
	 public List<Subject> listSubject();
	 public void updateSubject(Subject sub);
	 public List<SubjectDto> findByStudent(User student ,Long field);
	 public List<Subject> getByFieldAndLevel(Long id,Long fieldId);
	 
	 
	 
	 
	// Du
		public List<RequiredSubject> listReq();
		public Subject create(SubjectCreateDto data);
		//public void createrequired(RequiredSubjectDto reqdata);
		public void saveSubjectWithRequiredSubjects(Subject subject, Set<RequiredSubject> requiredSubjects);
		public void updatesubject(SubjectCreateDto dto);
		/* public boolean CheckNameExists(String name); */
		public Page<SubjectDto> getListPage(Long fieldId,Long levelId, Pageable pageable);
		
		public void hideById(Long id);
		public void showById(Long id);
		public List<Subject> getByFieldIDAndLevel(Long FieldId, Long level);
		
		public boolean CheckNameExists(String name);
		public boolean existsByName(String name);
    


}
