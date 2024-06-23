package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.ClassSubject;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassSubjectEditOneDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.RoomRegistedService;
import com.aptech.group3.service.RoomService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.TeacherSubjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import shared.BaseMethod;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping({ "/admin/class" })
public class ClassSubjectController {

	@Autowired
	private SemesterService SemesterService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ClassForSubjectService classSubjectService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomRegistedService roomRegistedService;

	@Autowired
	private TeacherSubjectService teacherService;

	@Autowired
	private StudentClassService studentClassService;

	@PostMapping("/update/status")
	public String updateStatusAction(HttpServletRequest request, RedirectAttributes rm) {
		String[] listField = new String[] {};
		List<Long> listData = new ArrayList<Long>();
		ClassSubject status = ClassSubject.valueOf(request.getParameter("type_update"));

		if (request.getParameterValues("class[]") == null) {
			rm.addAttribute("error",ActionStatus.ERROR_NULL);
			return "redirect:/admin/class/update/status";
		} else {
			listField = request.getParameterValues("class[]");
			for (int i = 0; i < listField.length; i++) {
				listData.add(Long.parseLong(listField[i]));
			}
		}

		if (status == ClassSubject.REGISTED) {
			AtomicBoolean exit = new AtomicBoolean(false);
			List<Integer> countStudent = new ArrayList<Integer>();
			listData.forEach(e -> {
				ClassForSubject check = classSubjectService.findById(e);
				List<StudentClass> data = studentClassService.findByClassForSubjectId(e);
				countStudent.add(data.size());
				if (check.getStatus() != ClassSubject.REGISTING) {
					exit.set(true);
				}
			});
			// redirect if list class have any class have status not registing
			if (exit.get()) {
				rm.addAttribute( "error",ActionStatus.ERROR_TYPE_LEARNING);
				return "redirect:/admin/class/update/status";
			}
			//if > min quantity delete list waiting list
			//if< min  delete all student registered class, delete class, delete room registed
			for (int i = 0; i < listData.size(); i++) {
				ClassForSubject check = classSubjectService.findById(listData.get(i));
				if (countStudent.get(i) < check.getMinQuantity()) {
					studentClassService.DeleteByClassIdAndStatus(listData.get(i), null);
					roomRegistedService.delete(check.getId(), check.getRoom().getId());
					classSubjectService.delete(listData.get(i));
					
				} else {
					studentClassService.DeleteByClassIdAndStatus(listData.get(i), ClassStatus.WAITINGLIST);
					studentClassService.updateStatusToPaid();
				}
			}
		}

		if (status == ClassSubject.LEARNING) {
			AtomicBoolean exit = new AtomicBoolean(false);
			List<Integer> countStudent = new ArrayList<Integer>();
			listData.forEach(e -> {
				ClassForSubject check = classSubjectService.findById(e);
				List<StudentClass> data = studentClassService.getListByClassIdAndStatus(e, ClassStatus.PAID);
				countStudent.add(data.size());
				if (check.getStatus() != ClassSubject.REGISTED) {
					exit.set(true);
				}
			});
			
			if (exit.get()) {
				rm.addAttribute("error",ActionStatus.ERROR_TYPE_REGISTERED);
				return "redirect:/admin/class/update/status";
			}
			
			//if student paid > min delete unpaid set student status llearning
			//if student paid <min delete class and delete all student registed class delete room registed
			for (int i = 0; i < listData.size(); i++) {
				
				ClassForSubject check = classSubjectService.findById(listData.get(i));
				
				if (countStudent.get(i) < check.getMinQuantity()) {
					studentClassService.DeleteByClassIdAndStatus(listData.get(i), null);
					roomRegistedService.delete(check.getId(), check.getRoom().getId());
					classSubjectService.delete(listData.get(i));
				} else {
					studentClassService.DeleteByClassIdAndStatus(listData.get(i), ClassStatus.UNPAID);
					studentClassService.updateManyStudentStatusByClassId(listData.get(i), ClassStatus.LEARNING);
				
				}
			}
		}


		classSubjectService.updateClassStatus(status, listData);
		rm.addAttribute("error",ActionStatus.UPDATED);
		return "redirect:/admin/class/update/status";
	}

	@GetMapping("/update/status")
	public String updateStatus(Model model, @RequestParam(name = "subject", required = false) Long sj,
			@RequestParam(name = "semester", required = false) Long se,
			@RequestParam(name = "status", required = false) ClassSubject status,
			@RequestParam(name = "error", required = false) ActionStatus error,
			@AuthenticationPrincipal CustomUserDetails currentUser) {

		Long subjectId = sj == null ? 0 : sj;
		Long fieldId = currentUser.getUser().getFields().get(0).getId();
		ClassSubject currentStatus = status == null ? ClassSubject.ALL : status;

		Long selectSemester = se == null ? SemesterService.getCurrentSemester().getId() : se;

		List<ClassForSubject> data = classSubjectService.getListNoPage(status == ClassSubject.ALL ? null : status,
				fieldId, selectSemester, subjectId == 0 ? null : sj);
		
		if(error!=null) {
			model.addAttribute("error", error.toString());
		}
		model.addAttribute("data", data);
		model.addAttribute("semesters", SemesterService.findAll());
		model.addAttribute("currentSemester", selectSemester);
		model.addAttribute("currentSubject", subjectId);
		model.addAttribute("subjects", subjectService.getByField(fieldId));
		model.addAttribute("status", ClassSubject.values());
		model.addAttribute("currentStatus", currentStatus);

		return "class_subject/statusUpdate";

	}

	@GetMapping("/edit/{id}")
	public String showEdit(Model model, @PathVariable(name = "id") Long id) {

		ClassSubjectEditOneDto data = classSubjectService.getEditDto(id);
		model.addAttribute("semester", SemesterService.getCurrentSemester());
		model.addAttribute("error", 1);
		model.addAttribute("data", data);
		model.addAttribute("current", classSubjectService.findById(id));

		return "class_subject/edit";
	}

	@PostMapping("/edit/one")
	public String editOne(Model model, @ModelAttribute("data") @Valid ClassSubjectEditOneDto dto,
			BindingResult bindingResult,RedirectAttributes rm) {
		ClassForSubject check = classSubjectService.findById(dto.getId());

		if (bindingResult.hasErrors()) {
			ClassSubjectEditOneDto data = classSubjectService.getEditDto(dto.getId());
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			model.addAttribute("error", 1);
			model.addAttribute("data", data);
			model.addAttribute("current", classSubjectService.findById(dto.getId()));
			model.addAttribute("room", roomService.getAvailableRoom(data.getQuantity(), data.getWeekDay(),
					data.getSlotStart(), data.getDateStart(), data.getDateEnd()));
			model.addAttribute("teacher", teacherService.getAvailableTeacher(data.getSubject_id().intValue(),
					data.getWeekDay(), data.getSlotStart(), data.getDateStart(), data.getDateEnd()));
			return "class_subject/edit";
		}

		if (check.getRoom().getId() != dto.getRoom_id()) {
			roomRegistedService.update(check.getId(), dto.getRoom_id());
		}

		classSubjectService.Edit(dto);
		rm.addAttribute("error",ActionStatus.UPDATED);
		return "redirect:/admin/class/list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable(name = "id") Long id) {

		model.addAttribute("data", classSubjectService.findById(id));
		return "class_subject/detail";
	}

	@GetMapping("/list")
	public String showByField(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(name = "subject", required = false) Long sj,
			@RequestParam(name = "semester", required = false) Long se,
			@RequestParam(name = "status", required = false) ClassSubject status,
			@RequestParam(name = "error", required = false) ActionStatus error,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);
		Long subjectId = sj == null ? 0 : sj;
		Long fieldId = currentUser.getUser().getFields().get(0).getId();
		ClassSubject currentStatus = status == null ? ClassSubject.ALL : status;

		Long selectSemester = se == null ? SemesterService.getCurrentSemester().getId().intValue() : se;

		Page<ClassForSubject> data = classSubjectService.getSubjectByFieldAndSemester(
				status == ClassSubject.ALL ? null : status, fieldId, selectSemester, subjectId == 0 ? null : sj,
				paging);

		model.addAttribute("data", data);
		model.addAttribute("semesters", SemesterService.findAll());
		model.addAttribute("currentSemester", selectSemester);
		model.addAttribute("currentSubject", subjectId);
		model.addAttribute("subjects", subjectService.getByField(fieldId));
		model.addAttribute("status", ClassSubject.values());
		model.addAttribute("currentStatus", currentStatus);
		if(error!=null) {
			model.addAttribute("error",error.toString());
		}
	

		return "class_subject/index";
	}

	@GetMapping("/create")
	public String showCreate(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		ClassSubjectCreateDto data = new ClassSubjectCreateDto();
		Semeter current = SemesterService.getCurrentSemester();
		Semeter nextSemeter;

		if (current.getName() == 1) {
			nextSemeter = SemesterService.getByYearAndName(current.getYear(), current.getName() + 1);
		} else {
			nextSemeter = SemesterService.getByYearAndName(current.getYear() + 1, current.getName() - 1);
		}

		model.addAttribute("semester", nextSemeter);
		model.addAttribute("data", data);
		model.addAttribute("error", 0);
		ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
		model.addAttribute("dataAll", dataAll);
		model.addAttribute("subjects", subjectService.getByField(currentUser.getUser().getFields().get(0).getId()));
		return "class_subject/create";
	}

	@PostMapping("/create")
	public String create(Model model, @ModelAttribute("data") @Valid ClassSubjectCreateDto data,
			BindingResult bindingResult,RedirectAttributes rm) {
		if (bindingResult.hasErrors()) {
			Semeter nextSemeter;
			Semeter current = SemesterService.getCurrentSemester();
			if (current.getName() == 1) {
				nextSemeter = SemesterService.getByYearAndName(current.getYear(), current.getName() + 1);
			} else {
				nextSemeter = SemesterService.getByYearAndName(current.getYear() + 1, current.getName() - 1);
			}
			model.addAttribute("semester", nextSemeter);
			model.addAttribute("data", data);
			Subject classCheck = subjectService.findbyId(data.getSubject_id()).orElse(null);
			model.addAttribute("selected_credit", classCheck.getCredit());

			model.addAttribute("lastSelectRoom",
					roomService.getAvailableRoom(data.getQuantity(), data.getWeekDay(), data.getSlotStart(),
							BaseMethod.convertDate(data.getDate_start()), BaseMethod.convertDate(data.getDate_end())));

			model.addAttribute("lastSelectTeacher",
					teacherService.getAvailableTeacher(data.getSubject_id().intValue(), data.getWeekDay(),
							data.getSlotStart(), BaseMethod.convertDate(data.getDate_start()),
							BaseMethod.convertDate(data.getDate_end())));
			ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
			model.addAttribute("dataAll", dataAll);
			model.addAttribute("subjects", subjectService.findAll());
			return "class_subject/create";
		}

		classSubjectService.create(data);

		rm.addAttribute("error",ActionStatus.CREATED);
		return "redirect:/admin/class/list";
	}

	@PostMapping("/createall")
	public String createAll(Model model, @ModelAttribute("dataAll") @Valid ClassSubjectAllDto dataAll,
			BindingResult bindingResult,RedirectAttributes rm) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			ClassSubjectCreateDto data = new ClassSubjectCreateDto();
			model.addAttribute("data", data);

			model.addAttribute("dataAll", dataAll);
			model.addAttribute("subjects", subjectService.findAll());
			model.addAttribute("error", 1);
			return "class_subject/create";
		}

		classSubjectService.createAll(dataAll);
		rm.addAttribute("error",ActionStatus.CREATED);
		return "redirect:/admin/class/list";
	}

}