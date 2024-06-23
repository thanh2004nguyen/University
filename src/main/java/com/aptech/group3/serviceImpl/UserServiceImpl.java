package com.aptech.group3.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aptech.group3.Dto.UpdateProfileDto;
import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.TokenRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.UserService;

import jakarta.servlet.ServletOutputStream;

import org.springframework.data.domain.Sort;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FiledService fieldService;

	@Autowired
	private FiledRepository fieldRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired 
	private TokenRepository tokenRepository;
	
	public Page<User> searchUsersByCode(String code, Pageable pageable) {
		return userRepository.findByCode(code, pageable);
	}
	
	
	 public List<User> getAllUsersExceptAdmin() {
	        return userRepository.findAllExceptAdmin();
	    }
 public List<User> findByCode(String code) {
     return userRepository.findByCode(code);
 }
 
	

  public List<User> findByFieldIdAndSubjectIdAndStatusAndCode(UserStatus status, Long fieldId) {
        return userRepository.findByFieldIdAndSubjectIdAndStatusAndCode(status, fieldId);
    }
  
	
	public void updateStatusForUsers(UserStatus status, List<Long> id) {
        userRepository.updateStatusStudent(status, id);
   }
	
	
	public void updateAvatar(Long userId, String newAvatarFilename) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

		// Cập nhật avatar của người dùng
		user.setAvatar(newAvatarFilename);
		userRepository.save(user);
	}
	
	public UserDto updateUserInfo(Long userId, UpdateProfileDto updateProfileDto) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// Kiểm tra xem hình ảnh đã thay đổi hay không
			if (!updateProfileDto.getAvatar().equals(user.getAvatar())) {
				// Nếu hình ảnh đã thay đổi, lưu tên hình ảnh mới vào cơ sở dữ liệu
				user.setAvatar(updateProfileDto.getAvatar());
			}

			// Cập nhật thông tin người dùng từ DTO
			user.setName(updateProfileDto.getName());
			user.setEmail(updateProfileDto.getEmail());
			user.setPhone(updateProfileDto.getPhone());
			user.setAddress(updateProfileDto.getAddress());
			user.setInfomation(updateProfileDto.getInfomation());

			// Lưu thông tin người dùng đã cập nhật vào cơ sở dữ liệu
			User updatedUser = userRepository.save(user);
			return convertToDto(updatedUser);
		}
		return null;
	}
	
	public UserDto convertToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhone(user.getPhone());
		userDto.setAddress(user.getAddress());
		userDto.setInfomation(user.getInfomation());
		userDto.setAvatar(user.getAvatar());
		// Thêm các trường khác nếu cần
		return userDto;
	}
	
	
	public Page<User> searchUsersByName(String name, Pageable pageable) {
		return userRepository.findByNameContainingIgnoreCase(name, pageable);
	}
	
    public boolean updateMobileCode( String mobileCode, Long userId) {
    	boolean result= false;
    	try {
    		userRepository.updateMobiCode(mobileCode, userId);
    		return true;
    		
    	}catch(Exception e) {
    		return result;
    	}
    }
    
    public List<User> getListUSerByField(UserStatus status,  Long fieldId, String role){
    return	userRepository.findStudentByStatusAndField(status, fieldId,  role);
    }
    
    public List<User>getListUserByRoleAnddStatus(UserStatus status ,String role ){
    	return userRepository.getListUserByRoleAndStatus(status, role);
    }
  
    
    
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Xóa tài khoản - token của tài khoản
            userRepository.delete(user);
            tokenRepository.deleteByUserId(user.getId());
        }
    }


	public String getGreeting() {
		LocalTime currentTime = LocalTime.now();
		if (currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isBefore(LocalTime.of(12, 0))) {
			return "Good morning ";
		} else if (currentTime.isAfter(LocalTime.of(12, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
			return "Good afternoon ";
		} else {
			return "Good evening ";
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		// Kiểm tra xem user có tồn tại trong database không?
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new CustomUserDetails(user);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public Page<User> findByRole(String type, Pageable pageable) {
		String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		System.out.println("ADMIN: "+ currentUserRole);
		if ("ALL".equals(type)) {
			if ("ADMIN".equals(currentUserRole)) {
                return userRepository.findAll(pageable); 
            }
			else {
                return userRepository.findByRoleNot("ADMIN", pageable);  // Other users can't see admin roles
            }
		 } else {
	            return userRepository.findByRole(type, pageable);
	        }
	}

//	public Page<User> findByRole(String role, Pageable pageable) {
//		return userRepository.findByRole(role, pageable);
//	}

	public UserDetails loadUserByUserid(Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get(); // Retrieve the User object from Optional
			CustomUserDetails userDetails = new CustomUserDetails(user);

			return userDetails;
		} else {
			System.out.println("User not found!");
			return null;
		}
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<Field> findFieldsByUserId(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return user.getFields();
		}
		return new ArrayList<>();
	}

	public void saveAndEncoderPassword(List<User> users) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		for (User user : users) {
			if (!isPasswordEncoded(user.getPassword())) {
				String encodedPassword = encoder.encode(user.getPassword());
				user.setPassword(encodedPassword);
			}
		}
		userRepository.saveAll(users); // Lưu vào cơ sở dữ liệu
	}

	private boolean isPasswordEncoded(String password) {
		return password.length() > 20;
	}

	public void saveUsers(List<User> users) {
		userRepository.saveAll(users);
	}

	public boolean checkIfEmailExists(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}

	public void create(UserCreateDto dto) {
		System.out.println("DAY la dto" + dto);
		User user = mapper.map(dto, User.class);
		List<Field> data = new ArrayList<>();
		if (dto.getFields() != null) {
			dto.getFields().forEach(e -> {
				data.add(fieldRepository.getById(e));
			});
		}

		user.setFields(data);

		userRepository.save(user);
	}

	public UserDetails loadUserByUserEmail(String email) {
		// Kiểm tra xem user có tồn tại trong database không?
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new CustomUserDetails(user);
	}

	public User getUserByUserEmail(String email) {
		// Kiểm tra xem user có tồn tại trong database không?
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return user;
	}

	public Optional<User> login(String email, String password) {
		System.out.print("emailllllllllllllll" + email);
		User user = userRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			return Optional.of(user);
		} else {
			return null;
		}

	}

	public List<User> getUsersByRole(String role) {
		return userRepository.findByRole(role);
	}

	public User findById(Long id) {
		return userRepository.getById(id);
	}

	public List<String> getFieldNamesByUserId(Long userId) {
		// Tìm người dùng theo id
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			// Xử lý trường hợp không tìm thấy người dùng
			return Collections.emptyList();
		}

		// Lấy danh sách các fields cho người dùng
		List<Field> fields = user.getFields();

		// Trích xuất tên trường từ danh sách các trường
		List<String> fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());

		return fieldNames;
	}

	public void update(UserCreateDto dto) {
		Optional<User> userOptional = userRepository.findById(dto.getId());

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			// Cập nhật thông tin từ DTO vào đối tượng User
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPhone(dto.getPhone());
			user.setAvatar(dto.getAvatar());
			user.setRole(dto.getRole());
			
			user.setAddress(dto.getAddress());
			user.setInfomation(dto.getInfomation());
			
		
			List<Field> selectedFields = fieldService.getFieldsByIds(dto.getFields());
			user.setFields(selectedFields);

			// Cập nhật đường dẫn hình ảnh mới
			user.setAvatar(dto.getAvatar());
			userRepository.save(user);
		} else {
			throw new UsernameNotFoundException("User not found!"); // Xử lý khi không tìm thấy người dùng
		}
	}
	
	  public void updateProfile(UpdateProfileDto dto) {
	        User currentUser = userRepository.findById(dto.getId())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        // Update only the specified fields
	        currentUser.setName(dto.getName());
	        currentUser.setEmail(dto.getEmail());
	        currentUser.setPhone(dto.getPhone());
	        currentUser.setInfomation(dto.getInfomation());
	        currentUser.setAddress(dto.getAddress());
	        // Preserve the fields relationship
	        // Preserve the fields relationship
	        if (dto.getFields() != null && !dto.getFields().isEmpty()) {
	            List<Field> fields = fieldRepository.findAllById(dto.getFields());
	            currentUser.setFields(fields);
	        } else {
	            // If no fields provided in DTO, preserve existing fields
	            List<Field> existingFields = currentUser.getFields();
	            currentUser.setFields(existingFields);
	        }
	        currentUser.setAvatar(dto.getAvatar());
	        userRepository.save(currentUser);
	    }

	public int countStudents() {
		return userRepository.countStdByRole("student");
	}

	public int countTeachers() {
		return userRepository.countTcByRole("teacher");
	}

	public int countEmployees() {
		return userRepository.countEmByRole("employee");
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public List<User> readUsersFromExcel(MultipartFile file) throws IOException {
		List<User> users = new ArrayList<>();
		int successfulCount = 0;
		int failedCount = 0;

		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0); // Giả sử người dùng ở trang đầu tiên

			Iterator<Row> rowIterator = sheet.iterator();
			// Skip header row
			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Giả sử thứ tự cột: Email, Name, Role, Infomation, Address
				Cell emailCell = row.getCell(1);
				String email = "";
				if (emailCell != null) {
					if (emailCell.getCellType() == CellType.NUMERIC) {
						email = String.valueOf((int) emailCell.getNumericCellValue());
					} else if (emailCell.getCellType() == CellType.STRING) {
						email = emailCell.getStringCellValue();
					}
				}
				if (checkIfEmailExists(email)) {
					System.out.println("Email already exists: " + email);
					continue;
				}

				Cell nameCell = row.getCell(2);
				String name = "";
				if (nameCell != null) {
					if (nameCell.getCellType() == CellType.NUMERIC) {
						name = String.valueOf((int) nameCell.getNumericCellValue());
					} else if (nameCell.getCellType() == CellType.STRING) {
						name = nameCell.getStringCellValue();
					}
				}

				Cell roleCell = row.getCell(3);
				String role = "";
				if (roleCell != null) {
					if (roleCell.getCellType() == CellType.NUMERIC) {
						role = String.valueOf((int) roleCell.getNumericCellValue());
					} else if (roleCell.getCellType() == CellType.STRING) {
						role = roleCell.getStringCellValue();
					}
				}

				Cell addressCell = row.getCell(4);
				String address = "";
				if (addressCell != null) {
					if (addressCell.getCellType() == CellType.NUMERIC) {
						address = String.valueOf((int) addressCell.getNumericCellValue());
					} else if (addressCell.getCellType() == CellType.STRING) {
						address = addressCell.getStringCellValue();
					}
				}

				Cell infomationCell = row.getCell(5);
				String infomation = "";
				if (infomationCell != null) {
					if (infomationCell.getCellType() == CellType.NUMERIC) {
						infomation = String.valueOf((int) infomationCell.getNumericCellValue());
					} else if (infomationCell.getCellType() == CellType.STRING) {
						infomation = infomationCell.getStringCellValue();
					}
				}

				Cell phoneCell = row.getCell(6);
				String phone = "";
				if (phoneCell != null) {
					if (phoneCell.getCellType() == CellType.NUMERIC) {
						phone = String.valueOf((int) phoneCell.getNumericCellValue());
					} else if (phoneCell.getCellType() == CellType.STRING) {
						phone = phoneCell.getStringCellValue();
					}
				}
				// Tạo mật khẩu ngẫu nhiên
				String randomPassword = UUID.randomUUID().toString().substring(0, 6);

//			int studentCount = userService.countStudents();
//			String code = "ST24" + String.format("%04d", studentCount + 1);
//			data.setCode(code);

				User user = new User();
				user.setEmail(email);
				user.setName(name);
				user.setRole(role);
				user.setAddress(address);
				user.setInfomation(infomation);
				user.setPhone(phone);
				user.setPassword(randomPassword);

				// Đặt các giá trị cho các trường khác

				users.add(user);
				successfulCount++;
				System.out.println("success" + successfulCount);
			}

			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			failedCount++;
			System.out.println("failed" + failedCount);
		}

		return users;
	}

//	public byte[] exportToExcel(List<User> students) throws IOException {
//  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//  Workbook workbook = new XSSFWorkbook();
//  Sheet sheet = workbook.createSheet("Students");
//  Row headerRow = sheet.createRow(0);
//  String[] headers = {"Name", "Email", "Phone", "Code"}; 
//  for (int i = 0; i < headers.length; i++) {
//      Cell cell = headerRow.createCell(i);
//      cell.setCellValue(headers[i]);
//  }
//  int rowNum = 1;
//  for (User student : students) {
//      Row row = sheet.createRow(rowNum++);
//      row.createCell(0).setCellValue(student.getName());
//      row.createCell(1).setCellValue(student.getEmail());
//      row.createCell(2).setCellValue(student.getPhone());
//      row.createCell(3).setCellValue(student.getCode());
//  }
//  try{workbook.write(outputStream);
//  workbook.close();
//  }catch(Exception e) {
//  	e.printStackTrace();
//  }
//  return outputStream.toByteArray();
//}

	public void exportToExcel(List<User> users, ServletOutputStream outputStream) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Students");
		int rowNum = 0;
		// Tạo dòng cho tiêu đề "Danh sách học sinh"
		Row titleRow = sheet.createRow(rowNum++);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("Danh sách học sinh");
		titleCell.setCellStyle(getCenteredCellStyle(workbook));
		// Gộp các ô thành một ô duy nhất cho tiêu đề "Danh sách học sinh"
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // Từ cột 0 đến cột 5

		// Tạo dòng cho header
		Row headerRow = sheet.createRow(rowNum++);
		String[] headers = { "Email", "Tên", "Phone", "Code", "Điểm", "Ký tên" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Thêm dữ liệu từ danh sách users
		for (User user : users) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(user.getEmail());
			row.createCell(1).setCellValue(user.getName());
			row.createCell(2).setCellValue(user.getPhone());
			row.createCell(3).setCellValue(user.getCode());
			// Thêm các cột "Mark" và "Sign" ở đây nếu có giá trị tương ứng từ đối tượng
			// User
		}

		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			// Xử lý ngoại lệ
			e.printStackTrace();
		}
	}

	private CellStyle getCenteredCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER); // Đặt căn giữa cho văn bản
		style.setVerticalAlignment(VerticalAlignment.CENTER); // Đặt căn giữa theo chiều dọc
		return style;
	}

	
	public void updatePassword (String password, Long id) {
		userRepository.updatePassword(password, id);
	}
	
	
}