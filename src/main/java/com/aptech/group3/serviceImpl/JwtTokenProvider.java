package com.aptech.group3.serviceImpl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aptech.group3.Repository.TokenRepository;
import com.aptech.group3.entity.Token;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${app.secretKey}")
	private String secretKey1;

	@Autowired private TokenRepository tokenRepository;
	// Thời gian có hiệu lực của chuỗi jwt
	public final int JWT_EXPIRATION = 60*10000;

	// Sử dụng UUID để tạo một refresh token ngẫu nhiên
	public String generateRefreshToken() {
		return UUID.randomUUID().toString();
	}

	// Tạo ra jwt từ thông tin user
	public String generateToken(CustomUserDetails userDetails) {
		
		
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		// Tạo chuỗi json web token từ id của user.
		return Jwts.builder().setSubject(Long.toString(userDetails.getUser().getId()))
				.claim("username", userDetails.getUsername()) // Thêm thông tin username vào payload
				.claim("password", userDetails.getPassword()) // Thêm thông tin password vào payload
				.claim("role", userDetails.getRole()) // Thêm role
				.setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secretKey1).compact();
	}

    // Save token to database
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }
	// Lấy thông tin user từ jwt
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey1).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey1).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

}
