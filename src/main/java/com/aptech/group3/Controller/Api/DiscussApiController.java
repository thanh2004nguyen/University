package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aptech.group3.Dto.DiscussMessageDto;
import com.aptech.group3.Dto.ListRoomApiDto;
import com.aptech.group3.service.DiscussMessageService;
import com.aptech.group3.service.DiscussRoomService;

@RestController
@RequestMapping({"/api/discuss"})
public class DiscussApiController {
	
	@Autowired private DiscussMessageService service;
	@Autowired private DiscussRoomService roomService;

	
	@GetMapping("/room/{id}")
	public ResponseEntity<List<DiscussMessageDto>> detailRoom(@PathVariable("id") Long roomId){
		List<DiscussMessageDto> data= service.getMessageByRoomId(roomId);
		return ResponseEntity.ok()
				.body(data);
	}
	
	@GetMapping("/list/{id}")
	public  ResponseEntity<List<ListRoomApiDto>> listDiscussRoom(@PathVariable("id") Long roomId){
		
		List<ListRoomApiDto> data= roomService.getListRoomApiDto(roomId);
		return ResponseEntity.ok().body(data);
	}
}