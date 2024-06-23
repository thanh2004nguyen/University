package com.aptech.group3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.NotifyCreateDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.NotiticationRepository;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Notification;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.NotificationService;
import com.aptech.group3.service.SemesterService;

@Service
public class NotificationServiceImpl implements  NotificationService {

	
	@Autowired private NotiticationRepository repo;
	
	@Autowired private ClassForSubjectRepository classRepo;
	
	@Autowired private FiledRepository fieldRepo;
	

	
	public void create(NotifyCreateDto dto, User creator,Semeter se) {
		
		Notification data= new Notification();
		data.setContent(dto.getContent());
		data.setCreated_at(dto.getCreated_at());
		data.setCreator(creator);
		data.setTitle(dto.getTitle());		
		data.setType(dto.getType());
		data.setSemester(se);
		
		if(dto.getClass_id()!=null) {
			classRepo.findById(dto.getClass_id()).ifPresent(data::setClassSbject);
		}
		if(dto.getField_id()!=null) {
			fieldRepo.findById(dto.getField_id()).ifPresent(data::setField);
		}
		
		if(dto.getTypeSent().contains("mobile")) {
		data.setSentMobile(true);
		}
		
		if(dto.getTypeSent().contains("email")) {
			data.setSentEmail(true);
		}
		
		repo.save(data);
		
	}
	
	public Page<Notification> getByFieldAndSemester(Long fieldId, Long semesterId,Pageable pageable){
		
		Page<Notification> data =repo.findBySemester_IdAndField_Id(semesterId, fieldId, pageable);
				
		return data;
	}
}
