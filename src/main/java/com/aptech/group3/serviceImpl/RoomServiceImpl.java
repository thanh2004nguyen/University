package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.entity.Room;
import com.aptech.group3.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	RoomRepository rr;

	public List<Room> getAvailableRoom(int capacity, int weekday, int start, Date dstart, Date dend) {
		List<Room>data= rr.availableRoom(capacity, weekday, start, dstart, dend);
		
		return data;
	}
	
	public Room getRoomById(Long Id) {
		return rr.findById(Id).orElse(null);
	}
	
	
	
	
}
