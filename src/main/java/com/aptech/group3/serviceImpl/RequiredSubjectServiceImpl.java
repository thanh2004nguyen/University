package com.aptech.group3.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.FieldDto;
import com.aptech.group3.Dto.RequiredSubjectDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.RequiredSubjectRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.RequiredSubjectService;

@Service
public class RequiredSubjectServiceImpl implements RequiredSubjectService {

    @Autowired
    private RequiredSubjectRepository requiredSubjectRepository;
    
    @Autowired
	SubjectRepository subRepo;
    
	public RequiredSubject findById(Long id) {
		return requiredSubjectRepository.findById(id).get();
	}
	
	
    
    public List<RequiredSubject> findListRequiredSubjectBySubjectId(Long subjectId)
    {
    	return requiredSubjectRepository.findBySubjectId(subjectId);
    }
    
    
	public RequiredSubject getStatus(String status) {
		return requiredSubjectRepository.findByStatus(status);
	}

	
	
	
	
	
	
	
	
	//Du
	public void createreq(RequiredSubjectDto dto) {
		RequiredSubject data = new RequiredSubject();
		subRepo.findById(dto.getSubjectId()).ifPresent(data :: setSubject);
		subRepo.findById(dto.getRequiredsubjectId()).ifPresent(data :: setRequiredsubject);
		data.setStatus(dto.getStatus());
		requiredSubjectRepository.save(data);
		
	}
	
	public void update(RequiredSubjectDto dto) {
		Optional<RequiredSubject> reqsubOption = requiredSubjectRepository.findById(dto.getId());
		if(reqsubOption.isPresent()) {
			RequiredSubject req = reqsubOption.get();
			//Cập nhật thông tin từ DTO vào đối tượng RequiredSubject
			req.setStatus(dto.getStatus());
			requiredSubjectRepository.save(req);			
		}else {
			throw new UsernameNotFoundException("RequiredSubject not found!");  // Xử lý khi không tìm thấy người dùng
		}
		
	}
	
	
	public List<RequiredSubject> findAll() {
		return requiredSubjectRepository.findAll();
	}


	
   
}