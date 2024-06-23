package com.aptech.group3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.User;






@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}
	


	/*
	 * public void run(String... args) throws Exception { // Khi chương trình chạy
	 * // Insert vào csdl một user. User user = new User();
	 * user.setUsername("loda"); user.setPassword(passwordEncoder.encode("loda"));
	 * userRepository.save(user); System.out.println(user); }
	 */

}
