package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.entity.Room;

public interface RoomService {
	public Room getRoomById(Long Id) ;
	
	public List<Room> getAvailableRoom(int capacity, int weekday, int start,  Date dstart, Date dend);

}
