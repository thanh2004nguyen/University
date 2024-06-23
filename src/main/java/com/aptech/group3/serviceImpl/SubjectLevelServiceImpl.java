package com.aptech.group3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.service.SubjectLevelService;

@Service
public class SubjectLevelServiceImpl implements SubjectLevelService{

	@Autowired
	SubjectLevelRepository sublvRepo;
	
	public SubjectLevel findById(Long levelId) {
		return sublvRepo.findById(levelId).get();
	}
	
	public SubjectLevel findByName(String name) {
		return sublvRepo.findByName(name);
	}
	
	public List <SubjectLevel> findAll() {
		return sublvRepo.findAll();
	}
	
}
