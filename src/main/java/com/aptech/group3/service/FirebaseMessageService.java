package com.aptech.group3.service;

import java.util.List;

import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FirebaseMessageService {

	@Autowired 
	private FirebaseMessaging firebaseMessaging;
	private String defaultImage="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg";
	public String sendNotificationByToken(NotificationMessage notificationMessage) {
		
		
		
		Notification notification = Notification.builder()
				.setTitle(notificationMessage.getTitle())
				.setBody(notificationMessage.getBody())
				.setImage(notificationMessage.getImage())
				.build();
		
		Message message= Message.builder()
				.setToken(notificationMessage.getRecripientToken())
				.setNotification(notification)
				.putAllData(notificationMessage.getData())
				.build();
		
		try {
			firebaseMessaging.send(message);
			return "Success send ing notification";
		}catch(FirebaseMessagingException e) {
			return "error exception occur";
		}
	}
	
	public void sentManyNotification(List<NotificationMessage> notificationMessage) {
		Notification notification = Notification.builder()
				.setTitle(notificationMessage.get(0).getTitle()==null ? "defalse" : notificationMessage.get(0).getTitle())
				.setBody(notificationMessage.get(0).getBody()== null ? "defalse" : notificationMessage.get(0).getBody() )
				.setImage(notificationMessage.get(0).getImage()== null ?defaultImage:notificationMessage.get(0).getImage() )
				.build();
		
		List<Message> messages= notificationMessage.stream().map(e->{
			Message message= Message.builder()
					.setToken(e.getRecripientToken())
					.setNotification(notification)
					.putAllData(e.getData())
					.build();
			
			return message;
		}).toList();
		
		try {
			firebaseMessaging.sendEach(messages, false);
		}catch(FirebaseMessagingException ex) {
			ex.getMessage();
		}
		
	}
}
