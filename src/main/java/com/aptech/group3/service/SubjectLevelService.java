package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.SubjectLevel;

public interface SubjectLevelService {
	public SubjectLevel findByName(String name);
	public SubjectLevel findById(Long levelId);
	public List <SubjectLevel> findAll();
}
