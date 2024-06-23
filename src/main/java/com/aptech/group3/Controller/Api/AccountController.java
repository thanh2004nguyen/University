package com.aptech.group3.Controller.Api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.LoginResultDto;
import com.aptech.group3.Dto.MarkSubjectShowApiDto;
import com.aptech.group3.Dto.MobileAppDto;
import com.aptech.group3.Dto.RefreshDto;
import com.aptech.group3.Dto.RefreshRecieveDto;
import com.aptech.group3.Dto.UpdateProfileDto;
import com.aptech.group3.Dto.UserApiDto;
import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.Repository.TokenRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.Token;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.LoginRequest;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.MarkSubjectService;
import com.aptech.group3.service.TokenService;
import com.aptech.group3.service.UserService;
import com.aptech.group3.serviceImpl.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.BaseMethod;

@RestController
public class AccountController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private UserRepository uRepo;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MarkSubjectService markService;
	
	@Autowired
	private ClassForSubjectService classService;
	
	@Autowired
	private LessonSubjectService lessonService;
	
	
	
	@GetMapping("/getClassMarksss")
	public  List<MarkSubject> getClassMarks(@RequestParam("classId") Long classId) {
	    List<MarkSubject> marks = markService.getListMarkSubjectByClassId(classId);
	    return marks;
	}
	
	 @GetMapping("/getMarkSubject2/{id}")
	    public ResponseEntity<List<MarkSubjectShowApiDto>> getMarkSubject2(@PathVariable("id") Long studentId) {
	        List<MarkSubject> markSubjects = markService.getMarksByStudentId(studentId);
	        List<MarkSubjectShowApiDto> markSubjectDtos = new ArrayList<>();
	        for (MarkSubject markSubject : markSubjects) {
	            List<ClassForSubject> classSubjects = markService.getClassForSubjectBySubjectId(markSubject.getSubject().getId());
	            Long classSubjectId = classService.getClassSubjectIdBySubjectId(markSubject.getSubject().getId());
	            List<LessonSubject> lessonSubjects = lessonService.getLessonsByClassSubjectId(classSubjectId);
	            System.out.println("CLASS: "+classSubjectId);
	            MarkSubjectShowApiDto markSubjectDto = new MarkSubjectShowApiDto();
	            markSubjectDto.setMarkSubject(markSubject);
	            markSubjectDto.setClassSubjects(classSubjects);
	            markSubjectDto.setLesson(lessonSubjects);
	            markSubjectDtos.add(markSubjectDto);
	        }

	        return ResponseEntity.ok().body(markSubjectDtos);
	    }
	 
	 
	 @GetMapping("/getMarkSubject/{id}")
	    public List<MarkSubject> getMarkSubject(@PathVariable("id") Long studentId) {
		 return markService.getMarksByStudentId(studentId);
	    }

	
	
	
	@PostMapping("/api/user/save/app")
	public ResponseEntity<?> updataeMobileApp( @RequestBody MobileAppDto data){
		System.out.print(data);
		boolean check=userService.updateMobileCode(data.getFmc(), data.getUserId());
		System.out.print(" check value print :"+check);
		if(check) {
			return ResponseEntity.ok().body("update success");	
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
	
	}
	
	


	
	// new
	
	@GetMapping("/api/user/info/{id}")
	public ResponseEntity<?>  getUserInfo(@PathVariable(name = "id") Long id) {
		User data= userService.findById(id);
		if(data==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			UserApiDto user= new UserApiDto();
			user.setAvatar(data.getAvatar());
			user.setName(data.getName());
			user.setRole(data.getRole());
			user.setCode(data.getCode());
			user.setUser_id(id);
			return ResponseEntity.ok().body(user);
		}		
	}

	@PostMapping("/api/login")
	public ResponseEntity<LoginResultDto> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			// Set the authentication information into the Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication.isAuthenticated()) {
				// Find the user by email
				User user = userService.findByEmail(loginRequest.getEmail());

				if (user != null) {
					// Generate the access token
					String accessToken = jwtTokenProvider.generateToken(new CustomUserDetails(user));

					// Generate the refresh token
					String refreshToken = jwtTokenProvider.generateRefreshToken();

					Token refreshTokenEntity = tokenService.createRefreshToken(user.getId(), refreshToken);

					// Update the JWT for the user in the token service
					tokenService.updateJwt(user.getId(), refreshToken);

					// Create the response DTO
					RefreshDto tokenDto = new RefreshDto(refreshTokenEntity.getToken(), accessToken);
					LoginResultDto loginResultDto = new LoginResultDto();
					UserApiDto userData= new UserApiDto();
					userData.setName(user.getName());
					userData.setCode(user.getCode());
					userData.setAvatar(user.getAvatar());
					userData.setRole(user.getRole());
					userData.setUser_id(user.getId());
					
					loginResultDto.setUser(userData);
					
					loginResultDto.setToken(tokenDto);

					// Return the response
					return ResponseEntity.ok().body(loginResultDto);
				} else {
					// If user is not found, return 404 Not Found
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				}
			} else {
				// If authentication failed, return 401 Unauthorized
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (Exception e) {
			// Handle any exceptions and return 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@GetMapping("/api/refreshtoken/{id}")
	public ResponseEntity<RefreshDto> RefeshToken(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Long id) {

		String jwtTokent = token.substring(7);
		System.out.println("TOKENNNN: " + jwtTokent);
		return ResponseEntity.ok(null);
	}

	@PostMapping("/api/register")
	public ResponseEntity<?> registerUser(@RequestBody UserCreateDto userDto) {
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
		boolean emailExists = userService.checkIfEmailExists(userDto.getEmail());
		if (emailExists) {
			return ResponseEntity.badRequest().body("Email already exists");
		}
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		// Tạo mới user từ DTO
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setName(userDto.getName());
		userCreateDto.setPhone(userDto.getPhone());
		userCreateDto.setEmail(userDto.getEmail());
		userCreateDto.setRole(userDto.getRole());
		userCreateDto.setPassword(encodedPassword);
		userService.create(userCreateDto);
		return ResponseEntity.ok("User registered successfully.");
	}

	@PostMapping("/api/refreshtoken")
	public ResponseEntity<RefreshDto> refreshToken(@RequestBody RefreshRecieveDto recieve) {
		String token = recieve.getToken();
		System.out.println(token);
		boolean checktoken = tokenService.checkTokenExpirationDate(token);
		if (checktoken) {
			String accessToken = jwtTokenProvider
					.generateToken(new CustomUserDetails(tokenService.getUserByToken(token)));
			System.out.println("TRUEEEEEEEEEEEE");
			return ResponseEntity.ok().body(new RefreshDto(token, accessToken));
		} else {
			System.out.println("FALSEEEEEEEEE");
			// If authentication failed, return 401 Unauthorized
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	  @GetMapping("/avatar/{id}")
	    public ResponseEntity<String> getUserAvatar(@PathVariable(name = "id") Long id) {
	        // Tìm người dùng theo ID
	        User user = userService.findById(id);
	        
	        // Nếu không tìm thấy người dùng, trả về mã lỗi 404 Not Found
	        if (user == null) {
	            return ResponseEntity.notFound().build();
	        } else {
	            // Nếu tìm thấy, trả về URL của avatar
	            return ResponseEntity.ok().body(user.getAvatar());
	        }
	    }
	
	@PutMapping("/updateApi/{userId}")
  public ResponseEntity<UserDto> updateUserProfile(@PathVariable Long userId, @RequestBody UpdateProfileDto updateProfileDto) {
      try {
          UserDto updatedUser = userService.updateUserInfo(userId, updateProfileDto);
          return ResponseEntity.ok().body(updatedUser);
      } catch (Exception ex) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
  }

	 @GetMapping("info/{id}")
	    public ResponseEntity<UserDto> getInfo(@PathVariable(name = "id") Long id) {
	        User data = userService.findById(id);
	        if (data == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } else {
	            UserDto user = new UserDto();
	            user.setId(data.getId());
	            user.setEmail(data.getEmail());
	            user.setCode(data.getCode());
	            user.setName(data.getName());
	            user.setPhone(data.getPhone());
	            user.setInfomation(data.getInfomation());
	            user.setRole(data.getRole());
	            user.setAddress(data.getAddress());
	            user.setAvatar(data.getAvatar());
	            return ResponseEntity.ok().body(user);
	        }
	        
	    }

	// FORGOT PASSWORD API



}