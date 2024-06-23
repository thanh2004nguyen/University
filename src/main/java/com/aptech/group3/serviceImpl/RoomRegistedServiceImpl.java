package com.aptech.group3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.RoomRegistedRepository;
import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.entity.RoomRegisted;
import com.aptech.group3.service.RoomRegistedService;

@Service
public class RoomRegistedServiceImpl implements RoomRegistedService {
	
	@Autowired 
	private RoomRegistedRepository repo;
	
	@Autowired
	private RoomRepository roomRepo;
	
	@Autowired
	private ClassForSubjectRepository classForSubjectReppository;
	public void update(Long classId,Long roomId) {
		
		RoomRegisted data=repo.findByClassRegistedId(classId);
		roomRepo.findById(roomId).ifPresent(data::setRoom);
		repo.save(data);
	}
	
	public void create (Long classId, Long roomId) {
		RoomRegisted data= new RoomRegisted();
		roomRepo.findById(roomId).ifPresent(data::setRoom);
		classForSubjectReppository.findById(classId).ifPresent(data::setClassRegisted);
		
		repo.save(data);
	
	}
	
	public void delete(Long classId, Long roomId) {
		RoomRegisted data=repo.findByClassRegisted_IdAndRoom_Id(classId, roomId);
		repo.delete(data);
	}
}
