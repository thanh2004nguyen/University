package com.aptech.group3.Controller;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unbescape.css.CssIdentifierEscapeLevel;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;

import com.aptech.group3.serviceImpl.StudentClassServiceImpl;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import groovyjarjarpicocli.CommandLine.Model.IGetter;
import jakarta.transaction.Transactional;

@Controller
@PreAuthorize("hasAuthority('STUDENT')")
@RequestMapping("/web/ClassRegister")
public class SubjectRegisterController {

	@Autowired
	private SubjectService subService;

	@Autowired
	private FiledRepository fRepository;

	@Autowired
	private StudentClassService studentsubservice;

	@Autowired
	private StudentClassRepository studentClassRepository;

	@Autowired
	private ClassForSubjectService classservice;

	@Autowired
	private UserServiceImpl userservice;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;
	
	@Autowired
	private ClassForSubjectService classForSubjectService;
	
	@Autowired
	private RequiredSubjectService requiredSubjectService;
	

	@GetMapping("/SubjectRegister")
	public String SubjectRegister(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		User user = userservice.getUserByUserEmail(currentUser.getUsername());
		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		List<Field> fields = user.getFields();

		model.addAttribute("fields", fields);
		model.addAttribute("subjectlevels", subjectLevels);
		return "page/RegisterSubject/New";
	}

	@PostMapping("/ClassRegister")
	@ResponseBody
	public ClassForSubjectDto ClassRegister(@RequestBody ClassForSubjectDto dto,
			@AuthenticationPrincipal UserDetails currentUser) {
		User user = userservice.getUserByUserEmail(currentUser.getUsername());
	
		studentsubservice.RegisterClass(dto, user.getId());
		return dto;
	}

	@PostMapping("/cancelClass")
	public String CancelClassRegister(@RequestParam Long classId) {
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

			// - quantity of CLass by 1
			int quantity = classs.getCurrentQuantity();
			quantity -= 1;
			classs.setCurrentQuantity(quantity);
			classForSubjectRepository.save(classs);

		}
		studentClassRepository.delete(studentclass);
		return "redirect:/web/ClassRegister/Ongoing";
	}

	@GetMapping("/listSubjectByStudentAndLevel")
	@ResponseBody
	public List<SubjectDto> listSubjectByStudentAndLevel(@RequestParam Long fieldId, @RequestParam Long subjectLevelId,@RequestParam boolean requiredSubject,
			 @AuthenticationPrincipal CustomUserDetails currentUser) {


		User student = currentUser.getUser();
		List<SubjectDto> listSubjects = new ArrayList<>();
		
		if(requiredSubject)
		{
			listSubjects=subService.findByStudentMoNeedRequiredSubjectCondition(fieldId);
		}
		else {
			listSubjects = subService.findByStudent(student, fieldId);
		}

		List<Subject> subjectsByLevel = new ArrayList<>();
		List<Subject> subjectThatHaveClass = new ArrayList<>();
		
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		List<ClassForSubject> listClassForSubjects = classForSubjectService.findByRegistrationDate(date);
		
		for(ClassForSubject list : listClassForSubjects) 
		{
	        
			subjectThatHaveClass.add(list.getSubject());
		}
		
		for(Subject aSubject :subjectThatHaveClass)
		{
		   if(aSubject.getSubjectlevel().getId().equals(subjectLevelId))
		   {
			   subjectsByLevel.add(aSubject);
		   }
		}

		List<SubjectDto> filteredSubjects = new ArrayList<>();
		for (SubjectDto subjectDto : listSubjects) {
			for (Subject subject : subjectsByLevel) {
				if (subject.getId().equals(subjectDto.getId())) {
					filteredSubjects.add(subjectDto);
					break;
				}
			}
		}

		return filteredSubjects;

	}
	@GetMapping("/listClassForSubject")
	@ResponseBody
	public List<ClassForSubjectDto> listClass(@RequestParam Long subjectId,
			@AuthenticationPrincipal UserDetails currentUser) {
		User student = userservice.getUserByUserEmail(currentUser.getUsername());
		// take RegisteringList of student
		List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(student.getId());
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		// take list Class of each subject
		List<ClassForSubjectDto> listclass = classservice.findBySubjectIdAndDate(subjectId, date);
		String[][] scheduleTable = addToSchedule(student.getId(), studentClasses);
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

	@GetMapping("/Ongoing")
	@Transactional
	public String showSchedule(Model model, @AuthenticationPrincipal UserDetails currentUser) {
		User student = userservice.getUserByUserEmail(currentUser.getUsername());
		List<StudentClass> studentClasses = new ArrayList<StudentClass>();
		List<StudentClass> studentClassesAll = studentsubservice.findSubjectByStudentId(student.getId());
		
	
		for(StudentClass aClass : studentClassesAll)
		{
		
			if(aClass.getStatus()== ClassStatus.LIST || aClass.getStatus()== ClassStatus.WAITINGLIST || aClass.getStatus()== ClassStatus.UNPAID || aClass.getStatus()== ClassStatus.PAID )
			{
				
				studentClasses.add(aClass);

			}
		}
		

         
         
     	for(StudentClass aClass : studentClasses)
		{
    		System.out.print(aClass.getStatus());
		}
		
     
		String[][] scheduleTable = addToSchedule(student.getId(), studentClasses);

		model.addAttribute("scheduleTable", scheduleTable);
		model.addAttribute("studentClasses", studentClasses);
		return "page/RegisterSubject/schedule";
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

}
