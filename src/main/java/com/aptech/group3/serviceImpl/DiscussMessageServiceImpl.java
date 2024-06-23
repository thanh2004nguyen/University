package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ChatMessage;
import com.aptech.group3.Dto.DiscussMessageDto;
import com.aptech.group3.Repository.DiscussMessageRepository;
import com.aptech.group3.Repository.DiscussRoomRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.DiscussMessage;
import com.aptech.group3.service.DiscussMessageService;


@Service
public class DiscussMessageServiceImpl implements DiscussMessageService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
DiscussMessageRepository repo;
	
	@Autowired
	DiscussRoomRepository discussRepo;
	
	@Autowired
	private ModelMapper mapper;
	

	public boolean DeleteMessage(Long messId) {
		boolean result= false;
		DiscussMessage data= repo.findById(messId).orElse(null);
		if(data== null ) {
			return result;
		}
		repo.delete(data);
		result= true;
		return result;
			
	}
	

	public DiscussMessage create(ChatMessage dto) {
		
		DiscussMessage data= new DiscussMessage();
		data.setCreateAt(new Date());
		userRepo.findById(dto.getId()).ifPresent(data::setStudent);
		discussRepo.findById(dto.getRoom_id()).ifPresent(data::setDiscuss);
		data.setText(dto.getContent());
		DiscussMessage result=repo.save(data);
		return result;
		
	}
	
	
	  public List<DiscussMessageDto> getMessageByRoomId (Long id){ 
		  List<DiscussMessageDto> data = repo.findByDiscuss_id(id).stream()
                  .map(e -> {
                      DiscussMessageDto dto = mapper.map(e, DiscussMessageDto.class);
                      dto.setSender_name(e.getStudent().getName());
                      dto.setSender_id(e.getStudent().getId());
                      dto.setTeacher(e.getStudent().getRole().equals("TEACHER"));
       
						return dto;
					}).toList();
		  return data;
	  
	  }
	
}
