package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aptech.group3.Dto.TimeTableDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.AttendanceService;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;

import shared.BaseMethod;

@Controller
@RequestMapping({ "/web/time_table" })
public class TimeTableController {

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private StudentClassService service;

	@Autowired
	private ClassForSubjectService classService;

	@GetMapping("/show")
	public String show(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(name = "semester", required = false) Long se,
			@RequestParam(name = "week", required = false, defaultValue = "1") int week) {

		Semeter currentSemester = semesterService.getCurrentSemester();
		List<TimeTableDto> weeks = new ArrayList<>();
		List<TimeTableShowDto> data = new ArrayList<TimeTableShowDto>();
		if (se == null) {

			weeks = semesterService.getListWeek(currentSemester.getId().intValue());
		} else {
			weeks = semesterService.getListWeek(se.intValue());

		}
		TimeTableDto currentWeek = new TimeTableDto();
		Date date = new Date();

		if (se != null) {
		
			weeks.forEach(e -> {

				if (e.getWeek() == week) {
					currentWeek.setWeek(e.getWeek());
					currentWeek.setEnd_day(e.getEnd_day());
					currentWeek.setStart_day(e.getStart_day());
				}
			});
		} else {
			System.out.println(" run 2");	
			AtomicBoolean exit = new AtomicBoolean(false);
			weeks.forEach(e -> {
				int cws = BaseMethod.customCompareDate(date, e.getStart_day());
				int cwe = BaseMethod.customCompareDate(date, e.getEnd_day());
				if (cws >= 0 && cwe <= 0) {
					exit.set(true);
					currentWeek.setWeek(e.getWeek());
					currentWeek.setEnd_day(e.getEnd_day());
					currentWeek.setStart_day(e.getStart_day());
				}
			});
			
			if(!exit.get()) {
				currentWeek.setEnd_day(weeks.get(0).getEnd_day())	;
				currentWeek.setStart_day(weeks.get(0).getStart_day());
				currentWeek.setWeek(weeks.get(0).getWeek());
			}

		}

		if (se != null) {
			System.out.println(" run 3");	
			model.addAttribute("currentSemester", semesterService.getSemesterById(se.intValue()));
		} else {
			
			model.addAttribute("currentSemester", currentSemester);
		}
		
		model.addAttribute("semester", semesterService.findAll());

		model.addAttribute("weeks", weeks);
		model.addAttribute("currentweek", currentWeek);

		Long studentId = currentUser.getUserId();
		TimeTableDto weekSelected = weeks.get(week - 1);

		if (currentUser.getUser().getRole().equals("TEACHER")) {

			data = classService.getTeacherTimeTable(studentId, currentSemester.getId(), currentWeek.getStart_day(),
					currentWeek.getEnd_day());

		}

		if (currentUser.getUser().getRole().equals("STUDENT")) {
			data = service.getCurrentTimeTable(studentId, currentWeek.getStart_day(), currentWeek.getEnd_day(),
					currentSemester.getId());

		}

		
		model.addAttribute("listsubject", data);
System.out.print(data);
		return "time_table/index";
	}

}
