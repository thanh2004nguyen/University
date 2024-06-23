package com.aptech.group3.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.SemeterDto;
import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.SemesterService;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping({"/admin/semester"})
public class SemeterController {
	@Autowired
	SemesterService smService;
	
	@GetMapping("/listsemester")
	public String Semester(Model model,@RequestParam(name = "error", required = false) ActionStatus error,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Semeter> sem = smService.findAll();
		
		model.addAttribute("sem",sem);
		if(error!=null) {
			model.addAttribute("error", error.toString());
		}
		return "semester/index";
	}
	
	@GetMapping("/createsemester")
	public String showcreate(Model model) {
		SemeterDto data = new SemeterDto();
		model.addAttribute("data",data);
		return "semester/create";
	}
	
	@PostMapping("/create")
	public String createSemester(Model model,@ModelAttribute("data")  SemeterDto data,
			BindingResult bindingResult, RedirectAttributes rm) {
		if(bindingResult.hasErrors()) {
			return"createSemester";
		}
		
		try {
			System.out.print(data);
			 Semeter sm = smService.create(data);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("dayend", "error.semesterDto", ex.getMessage());
            return "createSemester";
        }
		
		rm.addAttribute("error",ActionStatus.CREATED);
	return "redirect:/admin/semester/listsemester";
	}
	
	
}
