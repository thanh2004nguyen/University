package com.aptech.group3.Repository;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aptech.group3.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	@Query("SELECT t FROM Token t WHERE t.token like:token AND t.expirationDate < :day ")
	Token findByToken(String token,Date day);
	
	Token findByToken(String token);
	
	void deleteByUserId(Long userId);
	
	Token findByUserIdAndTokenType(Long id, String type);

	Token findByUserId(Long userId);
} 