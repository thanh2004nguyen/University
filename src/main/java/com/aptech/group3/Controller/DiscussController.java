package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aptech.group3.Dto.DiscussRoomDto;
import com.aptech.group3.Dto.DiscussRoomEditDto;
import com.aptech.group3.Dto.RoleList;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.DiscussRoom;
import com.aptech.group3.entity.Semeter;

import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.DiscussMessageService;
import com.aptech.group3.service.DiscussRoomService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/web/discuss" })
public class DiscussController {

	@Autowired
	SemesterService semesterService;

	@Autowired
	ClassForSubjectService classService;

	@Autowired
	DiscussMessageService messageService;

	@Autowired

	StudentClassService stuService;

	@Autowired
	DiscussRoomService service;

	@GetMapping("/list/class_attend")
	public String listClass(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<ClassForSubject> data = new ArrayList<ClassForSubject>();
		Semeter semester = semesterService.getCurrentSemester();
		if (currentUser.getRole().equals("TEACHER")) {
			data = classService.getByTeacherIdAndSemester(currentUser.getUserId(), semester.getId());
		}
		if (currentUser.getRole().equals("STUDENT")) {
			data = stuService.getListClassStudent(currentUser.getUserId(), semester.getId());
		}
		model.addAttribute("data", data);
		model.addAttribute("role", currentUser.getRole());

		return "discuss/list_class";
	}

	@GetMapping("/list/{id}")
	public String list(Model model, @PathVariable(name = "id") Long id,
			@AuthenticationPrincipal CustomUserDetails currentUser) {

		model.addAttribute("data", service.getList(id));
		model.addAttribute("role", currentUser.getUser().getRole());

		return "discuss/list";
	}

	@GetMapping("/edit/{id}")
	public String showEdit(Model model, @PathVariable(name = "id") Long id,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		if (!currentUser.getUser().getRole().equals(RoleList.TEACHER.toString())) {
			return "redirect:/discuss/room";
		} else {
			boolean checkTeacher = service.checkTeacherInRoom(id, currentUser.getUserId());
			if (!checkTeacher) {
				return "redirect:/web/discuss/room";
			}
		}
		DiscussRoomEditDto data = service.createEditDto(id);
		model.addAttribute("data", data);

		return "discuss/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(Model model, @PathVariable(name = "id") Long id,
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@ModelAttribute("data") @Valid DiscussRoomEditDto data, BindingResult bindingResult) {

		Long result = service.edit(data, id);

		return "redirect:/web/discuss/list/" + result;
	}

	@GetMapping("/create/{id}")
	public String showCreate(Model model, @PathVariable(name = "id") Long classId,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
	
		ClassForSubject check = classService.findById(classId);
		model.addAttribute("class", check);
		DiscussRoomDto data = new DiscussRoomDto();
		data.setClass_id(classId);
		model.addAttribute("data", data);
		return "discuss/create";
	}

	@PostMapping("/create")
	public String create(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@ModelAttribute("data") @Valid DiscussRoomDto data, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
	
			ClassForSubject check = classService.findById(data.getClass_id());
			model.addAttribute("class", check);
			model.addAttribute("data", data);
			return "discuss/create";
		}

		data.setTeacher_id(currentUser.getUserId());
		service.create(data);
		
		return "redirect:/web/discuss/list/"+data.getClass_id();

	}

	@GetMapping("/room")
	public String detail() {
		return "discuss/detail";
	}

	@GetMapping("/room/{id}")
	public String chat(Model model, @PathVariable(name = "id") int id,
			@AuthenticationPrincipal CustomUserDetails currentUser) {

		DiscussRoom data = service.getById(id);
		model.addAttribute("student", currentUser.getUserId());
		model.addAttribute("topic", data.getTopic());
		model.addAttribute("room", id);
		model.addAttribute("messages", messageService.getMessageByRoomId((long) id));
		if (currentUser.getUser().getRole().equals(RoleList.TEACHER.toString())) {
			boolean checkTeacher = service.checkTeacherInRoom((long) id, currentUser.getUserId());
			if (checkTeacher) {
				return "discuss/chat";
			} else {
				return "redirect:/web/discuss/room";
			}
		} else {
			boolean check = stuService.CheckStuentInClass(currentUser.getUserId(), data.getOwner().getId());
			if (!check) {
				return "redirect:/web/discuss/room";
			}
		}
		return "discuss/chat";
	}
}
