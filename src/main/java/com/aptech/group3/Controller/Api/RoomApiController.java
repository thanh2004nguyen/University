package com.aptech.group3.Controller.Api;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.Room;
import com.aptech.group3.service.RoomService;

import shared.BaseMethod;

@RestController 
@RequestMapping({"/api"})
public class RoomApiController {
	
	@Autowired
	RoomService roomService;
	
	@GetMapping("/public/room/available")
	public List<Room> getListAvailable( @RequestParam("capacity") int capacity, 
			@RequestParam("weekday")int weekday, @RequestParam("start")int start,  
			@RequestParam("dstart")String dstart,@RequestParam("dend") String dend) {
		 Date startd=BaseMethod.convertDate(dstart);
		 Date endd=BaseMethod.convertDate(dend);
		List<Room> data= roomService.getAvailableRoom(capacity,weekday,start,startd, endd);
		return data;
	};
	
	@GetMapping("/test")
	public String test() {
		return "hello";
	}

}
