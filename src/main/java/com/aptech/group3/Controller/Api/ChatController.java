package com.aptech.group3.Controller.Api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.aptech.group3.Dto.ChatMessage;
import com.aptech.group3.Dto.MessageReturnDto;
import com.aptech.group3.Dto.MessageType;
import com.aptech.group3.entity.DiscussMessage;
import com.aptech.group3.entity.DiscussRoom;
import com.aptech.group3.service.DiscussMessageService;
import com.aptech.group3.service.DiscussRoomService;

@Controller
public class ChatController {

	@Autowired
	private DiscussMessageService service;

	@Autowired
	private DiscussRoomService roomService;

	@MessageMapping("/chat.sendMessage/{roomId}")
	@SendTo("/topic/public/{roomId}")
	public MessageReturnDto sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) 
	{
		System.out.println("ssssssssssssssid"+chatMessage.getId());
		System.out.println("ssssssssssssssr"+chatMessage.getRoom_id());
		DiscussRoom roomData = roomService.getById(Integer.parseInt(roomId));
		Date currenrDate = new Date();
		
		if (currenrDate.after(roomData.getExpried())) {
			MessageReturnDto result = new MessageReturnDto();
			result.setSenderId(chatMessage.getId());			
			result.setType(MessageType.OVER);
			return result;
		}
		
		
		if(chatMessage.getType()==MessageType.DELETE) {
			MessageReturnDto result = new MessageReturnDto();
			int id= Integer.parseInt(chatMessage.getContent());
			result.setId((long)id);
			result.setType(MessageType.DELETE);
			result.setSenderId(chatMessage.getId());
			service.DeleteMessage((long)id);
			return result;
		}

		DiscussMessage res = service.create(chatMessage);

		MessageReturnDto result = new MessageReturnDto();
		result.setCreateAt(res.getCreateAt());
		result.setDiscussRoomId(res.getDiscuss().getId());
		result.setId(res.getId());
		result.setSenderId(res.getStudent().getId());
		result.setSenderName(res.getStudent().getName());
		result.setText(res.getText());
		result.setType(MessageType.CHAT);
		return result;
	}

	@MessageMapping("/chat.addUser/{roomId}")
	@SendTo("/topic/public/{roomId}")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable String roomId) {
		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}
