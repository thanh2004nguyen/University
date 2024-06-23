package com.aptech.group3.Controller.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;

import jakarta.servlet.http.HttpSession;

@RestController

@RequestMapping({ "/api/public/class" })
public class ListClassBySemester {
	@Autowired
	private ClassForSubjectService classService;
	
	@Autowired
	private StudentClassService studentClassService;
	
	@Autowired
	private SemesterService semesterService;
	
	
	
    @GetMapping("/classes")
    public List<ClassForSubject> getClasses(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return classService.findAll();
    }

    @GetMapping("/subjects")
    public List<Subject> getSubjects(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long teacherId = userDetails.getUserId();
        List<ClassForSubject> classes = classService.findByTeacherId(teacherId);
        return classes.stream()
                      .map(ClassForSubject::getSubject)
                      .distinct()
                      .collect(Collectors.toList());
    }


	@GetMapping("/current")
	public List<ClassForSubject> getCurrentClasses(Model model) {
		Long currentSemesterId = semesterService.getCurrentSemester().getId();
		List<ClassForSubject> currentClasses = classService.listClassBySemesterId(currentSemesterId);
	return currentClasses;
	}
//	@GetMapping("/{classId}/students")
//	public List<User> getClassStudentsBySubject(@PathVariable("classId") Long classId) {
//	    // Lấy danh sách các học sinh đang học môn học có subjectId trong lớp học có classId
//	    List<User> students = studentClassService.getStudentsByClassAndSubject(classId);
//	    return students;
//	}

	
	
    @GetMapping("/classes/{classId}/subjects")
    public List<ClassForSubject> getClassSubjects(@PathVariable("classId") Long classId) {
        List<ClassForSubject> subjects = classService.getClassSubjects(classId);
        return subjects;
    }
	
//	@GetMapping("/{classId}/subjects")
//	public String getClassSubjects(@PathVariable("classId") Long classId, Model model) {
//	    // Lấy danh sách các môn học của lớp dựa vào classId
//	    List<Subject> subjects = classService.getClassSubjects(classId);
//	    model.addAttribute("subjects", subjects);
//	    return "class_subjects"; // Trả về view hiển thị danh sách môn học
//	}
//	
    @GetMapping("/{classId}/students")
    public ResponseEntity<List<User>> getClassStudents(@PathVariable("classId") Long classId, HttpSession session) {
        // Lưu classId và subjectId vào Session
        session.setAttribute("classId", classId);

        List<User> students = studentClassService.getStudentsByClassAndSubject(classId);
        return ResponseEntity.ok().body(students);
    }

}
