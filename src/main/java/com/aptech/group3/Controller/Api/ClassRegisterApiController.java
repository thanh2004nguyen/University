package com.aptech.group3.Controller.Api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.conscrypt.OkHostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizService;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.serviceImpl.JwtTokenProvider;
import com.aptech.group3.serviceImpl.StudentClassServiceImpl;
import com.aptech.group3.serviceImpl.UserServiceImpl;

@RestController
public class ClassRegisterApiController {
	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;
	
@Autowired
private SubjectService subService;


@Autowired
private StudentClassRepository studentClassRepository;


@Autowired
AuthenticationManager authenticationManager;


@Autowired
private StudentClassServiceImpl studentsubservice;

@Autowired
private ClassForSubjectService classservice;


@Autowired
private UserRepository userRepository;

@Autowired
private ClassForSubjectService classForSubjectService;

@Autowired
private RequiredSubjectService requiredSubjectService;

@PostMapping("/api/ClassRegister")
@ResponseBody
public ResponseEntity<String> classRegister(@RequestBody Map<String, Long> requestBody) {
    Long classId = requestBody.get("classId");
    Long userId = requestBody.get("userId");

    // Check for conflict slot
    ClassForSubject classs = classForSubjectRepository.getById(classId);
    int newSlotStart = classs.getSlotStart();
    int newSlotEnd = classs.getSlotEnd();
    int newWeekday = classs.getWeekDay();
    String newSemesterType = classs.getType();

    List<StudentClass> studentClasses = studentsubservice.getStudentClasses(userId);
    String[][] scheduleTable = addToSchedule(userId, studentClasses);
    boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday, newSemesterType);
    
    if (hasConflict) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Schedule conflict detected. Unable to register for the class.");
    }

    studentsubservice.RegisterClassMobile(classId, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body("Class registered successfully.");
}


@PostMapping("/api/Ongoing")
@ResponseBody
public List<StudentClass> ListClassByStudent(@RequestBody Map<String, Long> requestBody) {
	 Long userId = requestBody.get("StudentId");
	List<StudentClass> studentClasses = studentsubservice.getStudentClasses(userId);
	return studentClasses;
}


@PostMapping("/api/cancelClass")
public ResponseEntity<String> CancelClassRegister(@RequestBody Map<String, Long> requestBody) {
	 Long classId = requestBody.get("ClassId");
	StudentClass studentclass = studentClassRepository.getById(classId);
	ClassForSubject classs = studentclass.getClassforSubject();
	List<StudentClass> waitingList = studentsubservice.findEarliestByStatus(ClassStatus.WAITINGLIST);
	// if have waitinglist , transfer earliest Student in WaitingList to List , and
	// no need to change quantity
	if (!waitingList.isEmpty()) {
		StudentClass tranStudentClass = waitingList.get(0);
		tranStudentClass.setStatus(ClassStatus.LIST);
		studentClassRepository.save(tranStudentClass);
	}

	// if have no WaitingList Student , - quantity of class by 1
	else {
		System.out.println("quantity fix");
		// - quantity of CLass by 1
		int quantity = classs.getCurrentQuantity();
		quantity -= 1;
		classs.setCurrentQuantity(quantity);
		classForSubjectRepository.save(classs);

	}
	studentClassRepository.delete(studentclass);
	return ResponseEntity.status(HttpStatus.ACCEPTED).body("Cancel Class Sucessfull");
}

@GetMapping("/api/public/listSubjectByLevelAndField/{levelId}/{fieldId}")
public List<Subject> GetAllSubjectByLevelAndField(@PathVariable Long levelId, @PathVariable Long fieldId) {
    return subService.getByFieldAndLevel(levelId, fieldId);
}



@PostMapping("/api/ClassRegister/list")
public List<SubjectDto> listSubject(@RequestBody Map<String, Long> requestBody) {
	Long studentId = requestBody.get("studentId");
		
	User student = userRepository.getById(studentId);
	List<SubjectDto> listSubjects = new ArrayList<>();
	

	listSubjects=subService.findByStudentMoNeedRequiredSubjectCondition((long) 0);


	List<Subject> subjectsByLevel = new ArrayList<>();
	List<Subject> subjectThatHaveClass = new ArrayList<>();
	
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	List<ClassForSubject> listClassForSubjects = classForSubjectService.findByRegistrationDate(date);
	
	for(ClassForSubject list : listClassForSubjects) 
	{
        
		subjectThatHaveClass.add(list.getSubject());
	}
	
	List<SubjectDto> filteredSubjects = new ArrayList<>();
	for (SubjectDto subjectDto : listSubjects) {
		for (Subject subject : subjectThatHaveClass) {
			if (subject.getId().equals(subjectDto.getId())) {
				filteredSubjects.add(subjectDto);
				break;
			}
		}
	}

	return filteredSubjects;
}




@PostMapping("/listClass")
public List<ClassForSubjectDto> listClass(@RequestBody Map<String, Long> requestBody) {
	Long studentId = requestBody.get("studentId");
	Long subjectId = requestBody.get("subjectId");
	User student = userRepository.getById(studentId);
	// take RegisteringList of student
	List<StudentClass> studentClasses = studentsubservice.getStudentClasses(studentId);
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	// take list Class of each subject
	List<ClassForSubjectDto> listclass = classservice.findBySubjectIdAndDate(subjectId, date);
	String[][] scheduleTable = addToSchedule(studentId, studentClasses);
	List<String> listRequiredSubject = new ArrayList<>();
	List<String> optionalRequiredSubjectList = new ArrayList<>();
	List<RequiredSubject> listreRequiredSubjects = requiredSubjectService.findListRequiredSubjectBySubjectId(subjectId);
	List<Subject> listPassedSupjects = subService.findPassesSubject(student);
	List<String> passesSubjectStrings = new ArrayList<String>();
	
	
	for(Subject aSubject : listPassedSupjects)
	{
		passesSubjectStrings.add(aSubject.getName());
	}
	
	System.out.print(passesSubjectStrings);
	
	for(RequiredSubject a: listreRequiredSubjects)
	{
		  if(a.getStatus().equals("PASS"))
		  {
			  listRequiredSubject.add(a.getRequiredsubject().getName());
		  }
		  else if(a.getStatus().equals("OPTIONAL"))
		  {
			  optionalRequiredSubjectList.add(a.getRequiredsubject().getName());
		  }		
	}
	System.out.print(listRequiredSubject);
	System.out.print(optionalRequiredSubjectList);
	
	  Boolean isPassedAllRequiredSubject = passesSubjectStrings.containsAll(listRequiredSubject);
	  Boolean isPassesOptionalRequiredSubject = false;
	  
	  if(optionalRequiredSubjectList.isEmpty())
	  {
		  isPassesOptionalRequiredSubject = true;
	  }
	  
	  else {
		  
		  for(String a: optionalRequiredSubjectList)
		  {
			  if(passesSubjectStrings.contains(a))
			  {
				  isPassesOptionalRequiredSubject = true;
			  }

		  }
        
	}
	  

		System.out.print("isPassesOptionalRequiredSubject"+isPassesOptionalRequiredSubject);
		System.out.print("isPassedAllRequiredSubject"+isPassedAllRequiredSubject);
	  
	
	for (ClassForSubjectDto i : listclass) {
		int newSlotStart = i.getSlotStart();
		int newSlotEnd = i.getSlotEnd();
		int newWeekday = i.getWeekDay();
		String newSemesterType = i.getType();
		// check conflict SLot
		boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday,
				newSemesterType);
		i.setConflict(hasConflict);
		i.setOptionalRequiredSuject(optionalRequiredSubjectList);
		i.setRequiredSubject(listRequiredSubject);
		i.setPassedSubjects(passesSubjectStrings);
		
		// check student match required subject?
		  if(!isPassedAllRequiredSubject || !isPassesOptionalRequiredSubject)
		  {
			  i.setIsHaveRequiredSubject(false);
		  }
		  else {
			  i.setIsHaveRequiredSubject(true);
		       }

		// check conflict subject name ( already have same subject in RegisteringList
		for (StudentClass s : studentClasses) {
			if (s.getClassforSubject().getSubject().getName().equals(i.getSubject().getName())) {
				i.setIsSameSubject(true);
				if (s.getClassforSubject().getId().equals((i.getId()))) {
					i.setIsSameClass(true);
				}
			} else {
				i.setIsSameSubject(false);
				i.setIsSameClass(false);
			}
		}
	}
			
	return listclass;
}


@GetMapping("/api/listcate")
public List<SubjectLevel> listCate() {

    return subService.listSubjectLevel();
}

private String[][] addToSchedule(Long studentId, List<StudentClass> studentClasses) {

	// Fake data representing schedule table
	String[][] scheduleTable = new String[12][12];
	for (StudentClass studentClass : studentClasses) {
		ClassForSubject classForSubject = studentClass.getClassforSubject();
		int slotStart = classForSubject.getSlotStart();
		int slotEnd = classForSubject.getSlotEnd();
		int weekday = classForSubject.getWeekDay();
		String semesterType = classForSubject.getType();
		String subjectInfo = classForSubject.getSubject().getName() + " " + classForSubject.getRoom().getName();

		for (int slot = slotStart; slot <= slotEnd; slot++) {
			if (semesterType.equals("all")) {
				// Populate both halves of the day
				scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;
				scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

			} else if (semesterType.equals("fhalf")) {
				// Populate only the first half of the day
				scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;

			} else if (semesterType.equals("lhalf")) {
				// Populate only the second half of the day
				scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

			}
		}
	}

	return scheduleTable;
}


private boolean checkForScheduleConflict(String[][] scheduleTable, int newSlotStart, int newSlotEnd, int newWeekday,
		String newSemesterType) {
	for (int slot = newSlotStart; slot <= newSlotEnd; slot++) {
		String existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)]; // Check the first half of the day
		String existingClassSecond = scheduleTable[slot - 1][2 * (newWeekday - 1) + 1];
		if (newSemesterType.equals("lhalf")) {
			if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
				return true;
			}
		}
		if (newSemesterType.equals("fhalf")) {
			if (existingClass != null && !existingClass.isEmpty()) {
				return true;
			}
		}

		if (newSemesterType.equals("all")) {

			if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
				return true;
			}

			existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)];
			if (existingClass != null && !existingClass.isEmpty()) {
				return true;
			}
		}
	}
	return false;
}








}
