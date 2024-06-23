package com.aptech.group3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.RoomRegisted;

public interface RoomRegistedRepository extends JpaRepository<RoomRegisted,Long> {

	@Query(" SELECT o FROM room_registed o WHERE o.classRegisted.id=:classId  ")
	public RoomRegisted findByClassRegistedId(Long classId);
	
	public RoomRegisted findByClassRegisted_IdAndRoom_Id(Long classRegistedId, Long roomId);
}
