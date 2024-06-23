package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.Dto.DetailAttendClassDto;
import com.aptech.group3.Dto.LessonApiDto;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.Semeter;

import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.AttendanceService;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;


@Controller
@RequestMapping({ "/web/attendance" })
public class AttendanceController {



	@Autowired
	private StudentClassService classService;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private LessonSubjectService lessonService;

	@Autowired
	private ClassForSubjectService classForsubjectService;

	@GetMapping("/process/{id}")
	public String process(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable(name = "id") Long classId) {

		DetailAttendClassDto result = new DetailAttendClassDto();

		List<String> countAttend = new ArrayList<String>();
		List<String> countAbsent = new ArrayList<String>();

		List<LessonSubject> lessons = lessonService.getByClassId(classId);

		List<Attendance> attendances = attendanceService.findByClassAndStudent(classId, currentUser.getUserId());

		result.setClassName(lessons.get(0).getClassSubject().getName());
		result.setSubjectName(lessons.get(0).getClassSubject().getSubject().getName());
		List<LessonApiDto> listLesson = new ArrayList<LessonApiDto>();

		lessons.forEach(l -> {
			LessonApiDto lessDto = new LessonApiDto();
			lessDto.setLesson(l.getLesson());
			lessDto.setDay(l.getDay());

			attendances.forEach(a -> {

				if (a.getLesson().getId() == l.getId()) {

					lessDto.setStatus(a.getStatus());
					System.out.println(a.getStatus());

				}

			});

			listLesson.add(lessDto);

		});

		result.setLessons(listLesson);

		result.getLessons().forEach(e -> {

			if (e.getStatus() != null) {
				if (e.getStatus().equals("attend")) {

					countAttend.add(e.getStatus());
				}

				if (e.getStatus().equals("absent")) {
					countAbsent.add("absent");
				}
			}

		});

		model.addAttribute("attend", countAttend.size());

		System.out.println(countAbsent.size());
		System.out.println(result.getLessons().size());

		boolean check = countAbsent.size() / result.getLessons().size() > 0.4 && countAbsent.size() != 0 ? false : true;

		model.addAttribute("data", result);
		model.addAttribute("normal", check);

		return "attendance/process";
	}

	@GetMapping("/list")
	public String list(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(name = "show", required = false, defaultValue = "all") String show,
			@RequestParam(name = "error", required = false) String error) {

		Long teacherId = currentUser.getUserId();
		List<ClassForSubject> data = new ArrayList<ClassForSubject>();
		Semeter semester = semesterService.getCurrentSemester();
		if (currentUser.getRole().equals("TEACHER")) {
			if ("today".equals(show)) {
				data = classForsubjectService.getClassesForToday(teacherId);
			} else {
				data = classForsubjectService.getByTeacherIdAndSemester(currentUser.getUserId(), semester.getId());
			};
		}
		if (currentUser.getRole().equals("STUDENT")) {
			if ("today".equals(show)) {
				data = classService.getTodayClassStudent(currentUser.getUserId(), semester.getId());
			} else {
				data = classService.getListClassStudent(currentUser.getUserId(), semester.getId());
			};
			//data = classService.getListClassStudent(currentUser.getUserId(), semester.getId());
		}

		model.addAttribute("data", data);
		model.addAttribute("show", show);

		return "attendance/list";
	}

	@GetMapping("/index/{id}")
	public String index(Model model, @PathVariable(name = "id") Long classId,
			@RequestParam(name = "code", required = false, defaultValue = "") String code,
			@AuthenticationPrincipal CustomUserDetails currentUser, RedirectAttributes redirectAttribute) {



		
		List<LessonSubject> currentLesson = lessonService.getCurrentLesson(classId, new Date());
		if (currentLesson.isEmpty()) {
			redirectAttribute.addFlashAttribute("notification", "No lesson");
	        return "redirect:/web/attendance/list?notification=No%20lesson";	
		}
		List<AttendanceDto> data;

		model.addAttribute("lesson", currentLesson.get(0));

		List<AttendanceDto> attended = attendanceService.getListAttendanceDto(currentLesson.get(0).getId(), new Date());
		if (code.isBlank()) {
			data = classService.getListStudentInClass(classId);
		} else {
			data = classService.getListStudentByCode(code, classId);
		}

		if (!attended.isEmpty()) {
			attended.forEach(e -> {
				data.forEach(d -> {
					if (d.getStudent_id() == e.getStudent_id()) {
						d.setStatus(e.getStatus());
					}
				});
			});
		}

		model.addAttribute("data", data);
		model.addAttribute("id", classId);

		return "attendance/index";
	}

}
