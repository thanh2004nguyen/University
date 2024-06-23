package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.DiscussMessage;

public interface DiscussMessageRepository extends JpaRepository<DiscussMessage,Long> {

	public List<DiscussMessage> findByDiscuss_id(Long id);
}
