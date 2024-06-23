package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.ChatMessage;
import com.aptech.group3.Dto.DiscussMessageDto;
import com.aptech.group3.entity.DiscussMessage;

public interface DiscussMessageService {

	public DiscussMessage create(ChatMessage dto);

	public List<DiscussMessageDto> getMessageByRoomId(Long id);

	public boolean DeleteMessage(Long messId);
}
