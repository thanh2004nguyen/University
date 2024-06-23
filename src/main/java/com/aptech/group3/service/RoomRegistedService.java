package com.aptech.group3.service;

public interface RoomRegistedService {

	public void update(Long classId,Long roomId);
	
	public void delete(Long classId, Long roomId);
	
	public void create (Long classId, Long roomId);
}
