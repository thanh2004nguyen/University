package com.aptech.group3.service;

import com.aptech.group3.entity.Token;
import com.aptech.group3.entity.User;

public interface TokenService {
	boolean checkToken( String token);
	public Token checkRefeshToken(Long userId);
	
	public Token checkAccessToken( Long userId) ;
	
	public Token createAccessToken( Long userId, String token);
	
	public Token createRefreshToken( Long userId, String token);
	
	public Token updateToken( Long userId, String token, String type, Long tokenId);
	
	public void updateJwt(Long userId, String jwt);
	
	public boolean checkTokenExpirationDate(String token);
	
	public User getUserByToken(String token);
}
