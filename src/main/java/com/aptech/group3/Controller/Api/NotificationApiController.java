package com.aptech.group3.Controller.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.model.NotificationMessage;
import com.aptech.group3.service.FirebaseMessageService;

@RestController
@RequestMapping("/api/test")
public class NotificationApiController {

	@Autowired
	FirebaseMessageService service;

	@PostMapping("/notify")
	public String sendNofify(@RequestBody NotificationMessage notificationMessage) {

		return service.sendNotificationByToken(notificationMessage);
	}
}
