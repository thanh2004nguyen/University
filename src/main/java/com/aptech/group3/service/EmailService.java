package com.aptech.group3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.NotifyCreateDto;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;

import java.util.List;
import java.util.Properties;
@Service
public class EmailService {
	

	
 	@Autowired
    private JavaMailSender emailSender;
    
 	public EmailService(JavaMailSender emailSender) {
 		this.emailSender = emailSender;
 	}


 
	 public void sendPasswordEmail(String email, String password) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("Thông tin đăng nhập mới");
	        message.setText("Thông tin : " + password);
	        emailSender.send(message);
	 }
	 
	 
	 public void sentManyEmail(NotifyCreateDto content, List<String> listReciever) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        String[] listEmail = listReciever.toArray(new String[0]);
	        System.out.println("list email " + listEmail.length +" hhhhhhhhh");
	      
	        message.setTo(listEmail);
	        message.setSubject(content.getTitle());
	        message.setText(content.getContent());
	        emailSender.send(message);
	 }
	 
	 
	 public boolean sendEmail(String subject, String message, String to) {
		boolean f = false;
		String from = "nguyenthaithanh101104@gmail.com";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.trust", host);
		properties.put("mail.smtp.auth", "false");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nguyenthaithanh101104@gmail.com", "sjem bdsa epdz dvue");
			}
		});
		session.setDebug(true);
		MimeMessage m = new MimeMessage(session);
		try {
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject(subject);
			m.setText(message);
			Transport.send(m);	
			f=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}


