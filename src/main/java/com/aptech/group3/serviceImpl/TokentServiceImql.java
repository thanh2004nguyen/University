package com.aptech.group3.serviceImpl;

import com.aptech.group3.serviceImpl.JwtTokenProvider;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.TokenRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.Token;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.TokenService;

import shared.BaseMethod;

@Service
public class TokentServiceImql implements TokenService {

	@Autowired
	TokenRepository repo;
	@Autowired
	UserRepository userRepo;

	public boolean checkToken(String token) {
		Date today = new Date();
		Token check = repo.findByToken(token, today);
		if (check == null) {
			return false;
		} else {
			return true;
		}
	}

	public Token checkRefeshToken(Long userId) {
		return repo.findByUserIdAndTokenType(userId, "refesh");
	}

	public Token checkAccessToken(Long userId) {
		return repo.findByUserIdAndTokenType(userId, "access");
	}

	public Token createAccessToken(Long userId, String token) {
		Calendar day = BaseMethod.toCalendar(new Date());
		day.add(Calendar.MINUTE, 1);

		Token newAccessToken = new Token();

		newAccessToken.setToken(token);
		newAccessToken.setTokenType("access");
		newAccessToken.setExpirationDate(day.getTime());
		userRepo.findById(userId).ifPresent(newAccessToken::setUser);
		Token result = repo.save(newAccessToken);

		return result;
	}

	public Token createRefreshToken(Long userId, String token) {
		Calendar day = BaseMethod.toCalendar(new Date());
		day.add(Calendar.MONTH, 6);
		;
		Token current= repo.findByUserId(userId);
		if(current!=null) {
		if(	BaseMethod.customCompareDate(new Date(), current.getExpirationDate())<0) {
			return current;
		}else {
			repo.delete(current);
		}
		}

		Token newAccessToken = new Token();

		newAccessToken.setToken(token);
		newAccessToken.setTokenType("refesh");
		newAccessToken.setExpirationDate(day.getTime());
		userRepo.findById(userId).ifPresent(newAccessToken::setUser);
		Token result = repo.save(newAccessToken);

		return result;
	}
	
	public void updateJwt(Long userId, String jwt) {
		Calendar day = BaseMethod.toCalendar(new Date());
		day.add(Calendar.MONTH, 6);
		User user = userRepo.findById(userId).orElse(null);
		
		Token token = repo.findByUserId(userId);
		token.setExpirationDate(day.getTime());
		token.setToken(jwt);
		repo.save(token);
		
	}

	public Token updateToken(Long userId, String token, String type, Long tokenId) {
		Token newToken = repo.findById(tokenId).orElse(null);
		Calendar day = BaseMethod.toCalendar(new Date());
		if (type.equals("access")) {
			day.add(Calendar.MINUTE, 1);
		} else {
			day.add(Calendar.MONTH, 6);
		}
		newToken.setToken(token);
		newToken.setTokenType(type.equals("refesh") ? type : "access");
		newToken.setExpirationDate(day.getTime());
		userRepo.findById(userId).ifPresent(newToken::setUser);
		Token result = repo.save(newToken);
		return result;
	}
	
	public boolean checkTokenExpirationDate(String token) {
		Token result = repo.findByToken(token);
		if(result!=null) {
			if(BaseMethod.customCompareDate(new Date(), result.getExpirationDate())<0) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
		
	}
	
	public User getUserByToken(String token) {
		Token user = repo.findByToken(token);
		return user.getUser();
	}
	

}
