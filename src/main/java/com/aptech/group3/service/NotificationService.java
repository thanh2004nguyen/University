package com.aptech.group3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.Dto.NotifyCreateDto;
import com.aptech.group3.entity.Notification;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.User;

public interface NotificationService {

	public Page<Notification> getByFieldAndSemester(Long fieldId, Long semesterId,Pageable pageable);
	public void create(NotifyCreateDto dto, User creator,Semeter se);
}
