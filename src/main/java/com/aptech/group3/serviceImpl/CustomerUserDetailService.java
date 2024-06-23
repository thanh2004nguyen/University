package com.aptech.group3.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;

import jakarta.transaction.Transactional;


@Service
public class CustomerUserDetailService implements UserDetailsService {
     
	@Autowired
	UserServiceImpl uservice;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	 @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
	     	  User user = userRepository.findByEmail(username);
	     	  
	     	  if(user!=null)
	     	  {

				  List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
				  GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
	              grantList.add(authority);				 
	              CustomUserDetails userDetails = new CustomUserDetails(user, user.getId());
				  return userDetails;
	     		  
	     	  }
	     	  else {
	           return null;
			}
	}
  
}
