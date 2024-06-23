package com.aptech.group3.Controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
	Random random = new Random(1000);
	
	static String verifyCode;
	
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private EmailService emailService;
	//email id form open handler
	@GetMapping("/forgot")
	public String openEmailForm() {
		return "forgot_email_password";
	}

	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session, Model model) {
	   // System.out.println("Emailllllllll: " + email);

	    // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu hay không
	    User user = userService.findByEmail(email);
	   // System.out.println("User::::::::::::::::: " + user);
	    if (user == null) {
	    	model.addAttribute("aaaerrorMessage", "Email does not exist!.");
	        return "forgot_email_password";
	    } else {
	    	int otp = random.nextInt(999999);
	    	String otpString = String.valueOf(otp);
	        System.out.println("OTPPPP: " + otpString);

	        String subject = "OTP Forgot Password";
	        String message = "OTP is:  " + otp;
	        String to = email;

	        //write code for send otp to email..
	        //boolean flag = this.emailService.sendEmail(subject, message, to);
	        emailService.sendPasswordEmail(email, otpString);
	       
	            session.setAttribute("myotp", otp);
	            session.setAttribute("email", email);
	            model.addAttribute("successMessage", "OTP sent successfully!");
	            return "verify_otp";
	        } 
	    
	}

    	
 
 
 @PostMapping("/verify-otp")
 public String verifyOtp(@RequestParam("otp") int otp, HttpSession session,  Model model) {
	 System.out.println("REquestOtp: ======: " + otp);
	    int myOtp = (int) session.getAttribute("myotp");
	    String email = (String) session.getAttribute("email");
	    
	    // Chuyển đổi chuỗi OTP thành số nguyên
	 if (myOtp != otp) {
		 model.addAttribute("errorMessage", "Invalid OTP. Please try again.");
	    	 	return "verify_otp";
	 }else { /*
			 * UUID uuid = UUID.randomUUID(); String newPass = uuid.toString().substring(0,
			 * Math.min(uuid.toString().length(), 6)); System.out.println("Passs:" +
			 * newPass); emailService.sendPasswordEmail(email,newPass); User user =
			 * uRepo.getUserByUsername(email); user.setPassword(bcrypt.encode(newPass));
			 * uRepo.save(user); return "redirect:/login";
			 */
		 return "change_form";
	 }
 }
 
 @PostMapping("/change-form")
 public String changePassword(@RequestParam("newpassword") String newPass, HttpSession session,  RedirectAttributes redirectAttributes) {
	 String email = (String)session.getAttribute("email");
	 User user = this.uRepo.getUserByUsername(email);
	 user.setPassword(this.bcrypt.encode(newPass));
	 this.uRepo.save(user);
	 redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
	    return "redirect:/login";
	 
 }
 

 
	@PostMapping("/flutter/forgot")
	public ResponseEntity<?> sendOTP(@RequestBody Map<String, String> request, HttpSession session) {
		Random random = new Random(1000);
		String email = request.get("email");
		User user = userService.findByEmail(email);

		if (user == null) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "Email does not exist.");
			return ResponseEntity.badRequest().body(response);
		} else {
			int otp = random.nextInt(999999);
			String otpString = String.valueOf(otp);

			String subject = "OTP Forgot Password";
			String message = "OTP is: " + otp;
			emailService.sendPasswordEmail(email, otpString);
			 // Lưu OTP vào session
	       verifyCode=otpString;
	        System.out.println("myotp"+ otp);
	        session.setAttribute("email", email);
			Map<String, String> response = new HashMap<>();
			response.put("otp", otpString);
			return ResponseEntity.ok(response); //nguyenthaithanh101104@gmail.com
		}
	}

	@PostMapping("/flutter/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request, HttpSession session) {
	    String email = request.get("email");
	    System.out.println("aaa"+email);
	    String otp = request.get("otp");
	 
	    System.out.println("otp:" + otp  + verifyCode);
	    
	    // Xác thực OTP
	    if (!otp.equals(verifyCode)) {
	        return ResponseEntity.badRequest().body(Map.of("message", "Invalid OTP."));
	    } else {
	        session.setAttribute("email", email);
	        UUID uuid = UUID.randomUUID();
	    	String newPass = uuid.toString().substring(0, Math.min(uuid.toString().length(), 6));
	    	System.out.println("Passs:" + newPass);
	        emailService.sendPasswordEmail(email,newPass);
	        
	        User user = uRepo.getUserByUsername(email);
	        user.setPassword(bcrypt.encode(newPass));
	        uRepo.save(user);
	        return ResponseEntity.ok(Map.of("message", "OTP verified successfully."));
	    }
	}
	 @PostMapping("/flutter/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
	        String email = request.get("email");
	        String newPassword = request.get("newPassword");

	        if (email == null || newPassword == null) {
	            return ResponseEntity.badRequest().body(Map.of("message", "Email and new password must be provided."));
	        }

	        User user = uRepo.getUserByUsername(email);
	        if (user == null) {
	            return ResponseEntity.badRequest().body(Map.of("message", "User not found."));
	        }

	        user.setPassword(bcrypt.encode(newPassword));
	        uRepo.save(user);

	        return ResponseEntity.ok(Map.of("message", "Password changed successfully."));
	    }

}
