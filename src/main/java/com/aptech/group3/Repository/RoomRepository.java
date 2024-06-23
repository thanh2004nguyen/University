package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Long>{

	@Query("SELECT r FROM room r WHERE r.capacity >= :capacity AND r.id NOT IN " +
			"(SELECT rr.room.id FROM room_registed rr WHERE rr.classRegisted.weekDay = :weekday " +
			"AND rr.classRegisted.slotStart <= :start AND rr.classRegisted.slotEnd >= :start " +
			"AND rr.classRegisted.dateStart <= :dstart AND rr.classRegisted.dateEnd >= :dend)")
			public List<Room> availableRoom(int capacity, int weekday, int start, Date dstart, Date dend);

	

	
}

