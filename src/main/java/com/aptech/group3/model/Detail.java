package com.aptech.group3.model;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aptech.group3.entity.User;
import com.aptech.group3.serviceImpl.UserServiceImpl;




@Service
public class Detail {
	@Autowired 
	UserServiceImpl service;
	
	   public User logindetail()
	   {
	   // lấy username ra từ authentication
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	
    	// lấy user ứng với username đã lấy đc
        User example = new User();
 
        List<User> list =(List<User>) service.loadUserByUserEmail(currentPrincipalName);
        User userdetail =list.get(0);
        return userdetail;
	   }
   
}

