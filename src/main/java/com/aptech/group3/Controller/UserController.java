package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.UpdateProfileDto;
import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping({ "/admin/user" })
public class UserController {
//https://bootsnipp.com/snippets/K0ZmK
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClassForSubjectService classService;

	@Autowired
	private StudentClassService studentService;

	@Autowired
	private FiledService filedService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private SemesterService semesterService;
	
	@PostMapping("/searchByCode")
	  public String searchByCode(@RequestParam(value = "code") String code,
	                             Model model) {
	      List<User> users = userService.findByCode(code);
	      List<Field> fieldsList = filedService.getAllFields();
	      model.addAttribute("fields", fieldsList);
	      model.addAttribute("statuses", UserStatus.values());
	      model.addAttribute("users", users);
	      return "admin_user/updateStatus";
	  }
	 @GetMapping("/search")
	    public String searchUsersByName(@RequestParam("name") String name,
	                                    @RequestParam(name = "page", defaultValue = "1") int page,
	                                    Model model) {
	        Pageable pageable = PageRequest.of(page - 1, 10);
	       
	        model.addAttribute("data", userService.searchUsersByCode(name, pageable));
	        model.addAttribute("currentPage", page);
	        model.addAttribute("searchTerm", name);
	        return "admin_user/index";
	    }

	public String saveUserProfile(Model model, @Valid @ModelAttribute("profileUser") UserCreateDto data,
			BindingResult result, @RequestParam(value = "avatar", required = false) MultipartFile avatarFile,
			HttpServletRequest request) throws IOException {
		boolean emailExists = userService.checkIfEmailExists(data.getEmail());
		if (emailExists) {
			model.addAttribute("error", "Email already exists");
			return "admin_user/create";
		}
		// lưu avatar vào uploads
		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = avatarFile.getInputStream();
			Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			data.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("notification", "Create success!");
		return null;
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable(name = "id") long id) {
		List<String> fieldNames = userService.getFieldNamesByUserId(id);
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("fields", fieldNames);
		return "admin_user/detail";
	}

	@GetMapping("/update/{id}")
 	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
 		User user = userService.findById(id);
 		List<Field> userFields = userService.findFieldsByUserId(id);
 		List<Field> allFields = filedService.getAllFields();
 		model.addAttribute("user", user);
 		model.addAttribute("userFields", userFields);
 		model.addAttribute("allFields", allFields);
 		return "admin_user/update";
 	}
 
 	@PostMapping("/update/{id}")
 	public String saveUpdate( Model model,@ModelAttribute("user") @Valid UserCreateDto dto, BindingResult result,
 			@RequestParam(name = "avatar", required = false) MultipartFile avatarFile,
 			@RequestParam(name = "id") Long id, RedirectAttributes rm,
 			HttpServletRequest request) {
 
 		System.out.println("ID: "+ id+ "DTO" + dto);
 		if (result.hasErrors()) {
 			List<String> fieldErrorsList = new ArrayList<>();
 			for (ObjectError e : result.getAllErrors()) {
 				String fieldErrors = ((FieldError) e).getField();   
 				fieldErrorsList.add(fieldErrors);
 				if (!fieldErrors.equals("avatar")) {
 					System.out.println("avatar");
 					model.addAttribute("data", dto);
 			        List<Field> userFields = userService.findFieldsByUserId(id);
 			        List<Field> allFields = filedService.getAllFields();
 			        model.addAttribute("userFields", userFields);
 			        model.addAttribute("allFields", allFields);
 					model.addAttribute("fieldErrors", fieldErrorsList);
 					return "admin_user/update";
 				} else {
 					continue;
 				}
 			}
 		}
 		
 		String[] listField = request.getParameterValues("fields");
 	    List<Long> listLong = new ArrayList<>();
 	    if (listField != null) {
 	        for (String a : listField) {
 	            listLong.add(Long.parseLong(a));
 	        }
 	    }

 	    // Set fields in dto, use empty list if listLong is empty
 	    dto.setFields(listLong.isEmpty() ? Collections.emptyList() : listLong);
 
 		User currentUser = userService.findById(id);
 		if (avatarFile != null && !avatarFile.isEmpty()) {
 			try {
 				Path path = Paths.get("uploads/");  
 				InputStream inputStream = avatarFile.getInputStream();
 				Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
 						StandardCopyOption.REPLACE_EXISTING);
 				dto.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		} else {
 			dto.setAvatar(currentUser.getAvatar());
 		}
 		

 		userService.update(dto);
 		rm.addAttribute("error",ActionStatus.UPDATED);
 		return "redirect:/admin/user/list";
 	}
	
	
	@GetMapping("/list")
	public String showByRole(@AuthenticationPrincipal CustomUserDetails currentUser, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "type", defaultValue = "ALL") String type,
			@RequestParam(name = "notification", required = false) String notification,
 			@RequestParam(name = "error", required = false) ActionStatus error) {
		Pageable paging = PageRequest.of(page - 1, 10);

		model.addAttribute("data", userService.findByRole(type, paging));
		model.addAttribute("type", type);
		model.addAttribute("selectedType", type);
		model.addAttribute("notification", notification);
		if(error!=null) {
			model.addAttribute("error",error.toString());
		}
	
		return "admin_user/index";
	}


	@GetMapping("/create")
	public String createUser(Model model) {
		UserCreateDto data = new UserCreateDto();
		List<Field> fieldsList = filedService.getAllFields();
		model.addAttribute("fields", fieldsList);
		model.addAttribute("data", data);
		return "admin_user/create";
	}

	@PostMapping("/create")
	public String saveUser(Model model, @RequestParam(name = "image", required = false) MultipartFile avatarFile,
			@ModelAttribute("data") @Valid UserCreateDto data, BindingResult result, HttpServletRequest request, RedirectAttributes rm)
			throws IOException {
		String[] listField = request.getParameterValues("field[]");
		if (result.hasErrors()) {

			List<Field> fieldsList = filedService.getAllFields();
			model.addAttribute("data", data);
			for (ObjectError error : result.getAllErrors()) { // 1.
				String fieldErrors = ((FieldError) error).getField(); // 2.
			}
			model.addAttribute("fields", fieldsList);
			return "admin_user/create";
		}
		if (data.getRole().equals("STUDENT")) {
			int studentCount = userService.countStudents();
			String code = "ST24" + String.format("%04d", studentCount + 1);
			data.setCode(code);
		} else if (data.getRole().equals("TEACHER")) {
			int teachersCount = userService.countTeachers();
			String code = "TC24" + String.format("%04d", teachersCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			if (listField != null) {
				for (String a : listField) {
					listLong.add((long) Integer.parseInt(a));
				}
				data.setFields(listLong);
			} else {
				data.setFields(null);
			}

		} else if (data.getRole().equals("EMPLOYEE")) {
			int employeeCount = userService.countEmployees();
			String code = "EM24" + String.format("%04d", employeeCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			if (listField != null) {
				for (String a : listField) {
					listLong.add((long) Integer.parseInt(a));
				}
				data.setFields(listLong);
			} else {
				data.setFields(null);
			}
		}
		boolean emailExists = userService.checkIfEmailExists(data.getEmail());
		if (emailExists) {
			model.addAttribute("error", "Email already exists");
			return "admin_user/create";
		}
		// lưu avatar vào uploads
		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = avatarFile.getInputStream();
			Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			data.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// tạo_mật_khẩu_gửi_tới_email
		String randomPassword = UUID.randomUUID().toString().substring(0, 6);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(randomPassword);
		data.setPassword(encodedPassword);
		userService.create(data);
		emailService.sendPasswordEmail(data.getEmail(), randomPassword);
		model.addAttribute("notification", "Create success!");
		rm.addAttribute("error",ActionStatus.CREATED);
		return "redirect:/admin/user/list?notification=Create%20success!";

	}
	
	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) throws IOException {
	    Long classId = (Long) session.getAttribute("classId");
	    Long subjectId = (Long) session.getAttribute("subjectId");
	    List<User> students = studentService.getStudentsByClassAndSubject(classId);
	    if (students.isEmpty()) {
	    	  redirectAttributes.addFlashAttribute("notification", "Class has no students yet!");
	          response.sendRedirect("/admin/user/list?notification=Class%20has%20no%20students%20yet!");
	          return;
	    }
	    
	    
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
	    
	    userService.exportToExcel(students, response.getOutputStream());
	}

	@PostMapping("/upload")
	public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
		try {
//			List<User> users = userService.readUsersFromExcel(file);
//			for (User user : users) {
//				emailService.sendPasswordEmail(user.getEmail(), user.getPassword());
//			}
//			userService.saveAndEncoderPassword(users);
//			return "redirect:/admin/user/list";
			
			
	        List<User> users = userService.readUsersFromExcel(file);
	        int studentCount = userService.countStudents();
	        int teacherCount = userService.countTeachers();
	        int employeeCount = userService.countEmployees();
	        for (User user : users) {
	        	
	            if (user.getRole().equals("STUDENT")) {
	            	studentCount ++;
	                String code = "ST24" + String.format("%04d", studentCount);
	                user.setCode(code);
	            } else if (user.getRole().equals("TEACHER")) {
	            	teacherCount ++;
	                String code = "TC24" + String.format("%04d", teacherCount);
	                user.setCode(code);
	            } else if (user.getRole().equals("EMPLOYEE")) {
	                employeeCount ++;
	                String code = "EM24" + String.format("%04d", employeeCount );
	                user.setCode(code);
	            }
	            emailService.sendPasswordEmail(user.getEmail(), user.getPassword());
	        }
	        userService.saveAndEncoderPassword(users);
	        return "redirect:/admin/user/list";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file. Please try again.";
		}
	}

	
	
	@PostMapping("/profile/avatar")
	public String updateAvatar(@RequestParam(name = "avatar", required = false) MultipartFile avatarFile,
			@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
		User user = userService.findById(userId);

		if (avatarFile != null && !avatarFile.isEmpty()) {
			try {
				Path path = Paths.get("uploads/");
				InputStream inputStream = avatarFile.getInputStream();
				Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				String newAvatarFilename = avatarFile.getOriginalFilename().toLowerCase();
				userService.updateAvatar(userId, newAvatarFilename);
				redirectAttributes.addAttribute("newAvatar", user.getAvatar());
				redirectAttributes.addAttribute("newName", user.getName());
				redirectAttributes.addAttribute("newEmail", user.getEmail());
				redirectAttributes.addAttribute("newPhone", user.getPhone());
				redirectAttributes.addAttribute("newAddress", user.getAddress());
				redirectAttributes.addAttribute("newInfo", user.getInfomation());
				redirectAttributes.addFlashAttribute("success", "Avatar updated successfully!");
			} catch (IOException e) {
				e.printStackTrace();	
	            redirectAttributes.addFlashAttribute("error", "Failed to update avatar.");
			}
		}
		return "redirect:/admin/user/profile";
	}
	
	
	
	@GetMapping("/updateStatus")
	  public String updateStatus(@RequestParam(name = "status", required = false) UserStatus status,
	                             @RequestParam(name = "fieldId", required = false) Long fieldId,
	                             Model model, @RequestParam(name = "error", required = false) ActionStatus error) {
		if(error!=null) {
			model.addAttribute("error",error.toString());
		}
	      List<User> users = new ArrayList<User>();
	      if(status!=null || fieldId!=null) {
	    	  users = userService.findByFieldIdAndSubjectIdAndStatusAndCode(status, fieldId);
	    	  
	      }else {
	          users = userService.getAllUsersExceptAdmin();
	      }
	      List<Field> fieldsList = filedService.getAllFields();
			model.addAttribute("fields", fieldsList);
			model.addAttribute("curenStatus", status);
		    model.addAttribute("statuses", UserStatus.values());
	      model.addAttribute("users", users);
	      return "admin_user/updateStatus";
	  }
	  
	  @PostMapping("/updateStatus")
	  public String updateStatusForUsers(@RequestParam("select_type_student_update") String status,
	          @RequestParam("student[]") List<String> students,  RedirectAttributes rm) {
		  List<Long> list = new ArrayList<Long>();
		     System.out.println("STUDENTS: "+ students);
		     System.out.println("STATUS: "+ status);
		     UserStatus userStatus = UserStatus.valueOf(status);
		     students.forEach(e->{
		    	 list.add(Long.parseLong(e));
		     });
		     userService.updateStatusForUsers(userStatus, list);
		     rm.addAttribute("error",ActionStatus.UPDATED);
	      return "redirect:/admin/user/updateStatus";
	  }
}
