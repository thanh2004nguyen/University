package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.Dto.HolidayEditDto;
import com.aptech.group3.Dto.NotifyCreateDto;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.HolidayService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;

import jakarta.validation.Valid;
import shared.BaseMethod;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')") 
@RequestMapping({ "/admin/holiday" })
public class HolidayController {
	
	@Autowired private SemesterService semesterService;
	@Autowired private ClassForSubjectService classService;
	@Autowired private  HolidayService holidayService;
	@Autowired private LessonSubjectService lessonService;
	@Autowired private EmailService emailService;
	@Autowired private StudentClassService studentClassService;
	
	
	@GetMapping("/delete/{id}")
	public String delete( @PathVariable(name = "id") Long id,RedirectAttributes redirect) {
		
		Holiday data= holidayService.getById(id);
		
		if(BaseMethod.customCompareDate(new Date(), data.getDateStart())>=0) {
			
			redirect.addAttribute("error", ActionStatus.ERROR);
			return "redirect:/holiday/index";
		}
		lessonService.remove(id);
		holidayService.delete(id);
		
		redirect.addAttribute("error",ActionStatus.DELETED);
		return "redirect:/admin/holiday/index";
	}
	
	@GetMapping("/index")
	public String index(Model model,
			@RequestParam(name = "error",required = false ) ActionStatus error,
			@RequestParam(name = "success",required = false ) String success,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name="year", required = false) Integer year) {
		Semeter semester=semesterService.getCurrentSemester();
		int useYear = year==null ? semester.getYear() : year;
		Pageable paging = PageRequest.of(page - 1, 10);
		
		Page<Holiday> data= holidayService.getListByYear(useYear, paging);
		model.addAttribute("data", data);
		model.addAttribute("semesters",semesterService.findAll());
		if(error!=null) {
			model.addAttribute("error",error.toString());
		}
		
		return "holiday/index";
	}
	@GetMapping("/edit/{id}")
	public String showEdit(Model model,  @PathVariable(name = "id") Long id ,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		HolidayEditDto data= holidayService.getListEditDto(id);
		model.addAttribute("data",data);
	
		Semeter semester=semesterService.getCurrentSemester();
		model.addAttribute("class",classService.getAllByfieldAndSemester(semester.getId(), currentUser.getUser().getFields().get(0).getId()));
		return "holiday/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String edit(Model model,  @PathVariable(name = "id") Long id,@AuthenticationPrincipal CustomUserDetails currentUser,
				@ModelAttribute("data") @Valid  HolidayEditDto data ,BindingResult result,RedirectAttributes redirect) {
if(result.hasErrors()) {
	model.addAttribute("data",data);
	Semeter semester=semesterService.getCurrentSemester();
	model.addAttribute("class",classService.getAllByfieldAndSemester(semester.getId(), currentUser.getUser().getFields().get(0).getId()));
	return "holiday/edit";
}
	boolean check= holidayService.edit(data);
	if(check) {
		lessonService.CheckOrRemove(data);
		lessonService.updateLesson(data);
	}
	
	redirect.addAttribute("error",ActionStatus.UPDATED);
		return "redirect:/admin/holiday/index";
	}
	
	
	@GetMapping("/create")
	public String createShow(Model model,@AuthenticationPrincipal CustomUserDetails currentUser) {
		
		HolidayCreateDto dto = new HolidayCreateDto();
		model.addAttribute("data",dto); 

		Semeter semester=semesterService.getCurrentSemester();
		model.addAttribute("semester",semester);
		model.addAttribute("classes",classService.getAllByfieldAndSemester(semester.getId(), currentUser.getUser().getFields().get(0).getId()));
		
		
		return "holiday/create";
	}
	
	@PostMapping("/create")
	public String create(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@ModelAttribute("data") @Valid  HolidayCreateDto data ,BindingResult result,RedirectAttributes redirect ) {
		if(result.hasErrors()) {
			model.addAttribute("data",data); 
			Semeter semester=semesterService.getCurrentSemester();
			model.addAttribute("semester",semester);
			model.addAttribute("class",classService.getAllByfieldAndSemester(semester.getId(), currentUser.getUser().getFields().get(0).getId()));
			
			return "holiday/create";
		}
		Semeter semester=semesterService.getCurrentSemester();
		data.setYear(semester.getYear());
		
		HolidayEditDto recieve=	holidayService.create(data);
		List<LessonSubject> listRecieve=lessonService.updateLesson(recieve);
		List<String> lsitemailRecieve= new ArrayList<String>();
		listRecieve.forEach(e->{
			List<StudentClass> studentList= studentClassService.findByClassForSubjectId(e.getClassSubject().getId());
			studentList.forEach(eStudent->{
				lsitemailRecieve.add(eStudent.getStudent().getEmail());
			});
			NotifyCreateDto emailData= new NotifyCreateDto();
			emailData.setTitle("Notify change time table");
			emailData.setContent("<p> because have a holiday apply. your time table is change . plese check your time table </p>");
			emailService.sentManyEmail(emailData, lsitemailRecieve);
		});
	
		 
		redirect.addAttribute("error",ActionStatus.CREATED);
		 return "redirect:/admin/holiday/index";
	}
	

}
