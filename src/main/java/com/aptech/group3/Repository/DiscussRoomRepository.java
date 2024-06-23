package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.DiscussRoom;

public interface DiscussRoomRepository extends JpaRepository<DiscussRoom,Long> {
	
	public List<DiscussRoom> findByOwnerId(Long id);
	
	public DiscussRoom findById(int id);

	
	public DiscussRoom findByIdAndTeacherId(Long id,Long teacherId);

}
