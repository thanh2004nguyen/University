package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.aptech.group3.entity.Notification;

public interface NotiticationRepository  extends JpaRepository<Notification, Long> {
	
	public Page<Notification> findBySemesterIdAndFieldId(Long semesterId , Long fieldId, Pageable pageable);
	
	public Page<Notification> findBySemester_IdAndField_Id(Long semesterId, Long fieldId, Pageable pageable);
	

}
