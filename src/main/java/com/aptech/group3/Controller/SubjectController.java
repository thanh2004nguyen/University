package com.aptech.group3.Controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassType;
import com.aptech.group3.Dto.RequiredSubjectDto;
import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.SubjectRepository;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.SubjectLevelService;
import com.aptech.group3.service.SubjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/admin/subject" })
public class SubjectController {

	@Autowired
	private SubjectService subService;

	@Autowired
	private RequiredSubjectService reqService;

	@Autowired
	private SubjectLevelService sublvService;

	@Autowired
	private SubjectRepository subjectRepository;

	@GetMapping("/list")
	@Transactional
	public String Subject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,

			@RequestParam(name = "level", required = false) Long se,
			@RequestParam(name = "error", required = false) ActionStatus error,

			@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);

		Long fieldId = currentUser.getUser().getFields().get(0).getId();

		Page<SubjectDto> data = subService.getListPage(fieldId, se == null || se == 0 ? null : se, paging);
	
		
		List<RequiredSubject> listreRequiredSubjects = new ArrayList<>();

		for(SubjectDto a : data ) {
			listreRequiredSubjects = reqService.findListRequiredSubjectBySubjectId(a.getId());
			System.out.println("iddd"+a.getId());
			List<String> listRequiredSubject = new ArrayList<>();
			List<String> optionalRequiredSubjectList = new ArrayList<>();
			
			for(RequiredSubject a2: listreRequiredSubjects)
			{
				  if(a2.getStatus().equals("PASS"))
				  {
					  listRequiredSubject.add(a2.getRequiredsubject().getName());
				  }
				  else if(a2.getStatus().equals("OPTIONAL"))
				  {
					  optionalRequiredSubjectList.add(a2.getRequiredsubject().getName());
				  }		
			}
			//System.out.println("dataaaa;"+listRequiredSubject.toString());
			//System.out.println("dataaaa;"+optionalRequiredSubjectList.toString());
			a.setOptionalRequiredSuject(optionalRequiredSubjectList);
			a.setPassedSubjects(listRequiredSubject);
			
			if (listRequiredSubject.isEmpty()) {
				listRequiredSubject.add("not have");
			}

			if (optionalRequiredSubjectList.isEmpty()) {
				optionalRequiredSubjectList.add("not have");
			}
		}
		
		
		List<SubjectLevel> sblevel = sublvService.findAll();
		List<Subject> subject = subService.findAll();
		
		model.addAttribute("subject",subject);
		model.addAttribute("data", data);
		model.addAttribute("sblevels", sblevel);
		model.addAttribute("selectLevel", se == null ? 0 : se);
	
		
		if(error!=null) {
			model.addAttribute("error", error.toString());
		}
		return "subject/index";
	}

	@GetMapping("/create")
	public String showcreatesubject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		SubjectCreateDto data = new SubjectCreateDto();
		model.addAttribute("data", data);

		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		// List<Field>fields = subService.findall();
		// model.addAttribute("subjects",
		// subService.getByField(currentUser.getUser().getFields().get(0).getId()));
		model.addAttribute("requiredSubjects", new HashSet<RequiredSubject>());

		model.addAttribute("field", currentUser.getUser().getFields().get(0).getId());
		// model.addAttribute("fields",fields);
		model.addAttribute("subjectlevels", subjectLevels);
		model.addAttribute("type", ClassType.values());

		return "subject/create";
	}

	@PostMapping("/create")
	public String createsubject(Model model, @ModelAttribute("data") @Valid SubjectCreateDto data,
			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes rm
			,@AuthenticationPrincipal CustomUserDetails currentUser) {
	     System.out.print("aaaaaaaa"+ data.getCreditAction());
	     
	  // Check for duplicate name
	        if (subService.existsByName(data.getName())) {
	            bindingResult.rejectValue("name", "duplicate", "A subject with this name already exists.");
	        }
		  if(bindingResult.hasErrors()) {
		  
		       System.out.print("bbbbbb"+ bindingResult.toString());
			  model.addAttribute("data", data);

				List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
				// List<Field>fields = subService.findall();
				// model.addAttribute("subjects",
				// subService.getByField(currentUser.getUser().getFields().get(0).getId()));
				model.addAttribute("requiredSubjects", new HashSet<RequiredSubject>());

				model.addAttribute("field", currentUser.getUser().getFields().get(0).getId());
				// model.addAttribute("fields",fields);
				model.addAttribute("subjectlevels", subjectLevels);
				model.addAttribute("type", ClassType.values());
		 return "subject/create"; }
		 
		

		String[] listField = new String[] {};
		String[] listField1 = new String[] {};

		if (request.getParameterValues("field[]") != null) {
			listField = request.getParameterValues("field[]");
		}

		if (request.getParameterValues("field1[]") != null) {
			listField1 = request.getParameterValues("field1[]");
		}
		Subject newsub = subService.create(data);
		if (listField.length != 0) {

			for (String a : listField) {
				System.out.print("list filed :" + listField.length);
				RequiredSubjectDto dto = new RequiredSubjectDto();
				dto.setRequiredsubjectId((long) Integer.parseInt(a));

				dto.setSubjectId(newsub.getId());
				dto.setStatus("PASS");

				reqService.createreq(dto);
			}
		}
		if (listField1.length != 0) {

			for (String a : listField1) {
				System.out.print("list filed :" + listField.length);
				RequiredSubjectDto dto = new RequiredSubjectDto();
				dto.setRequiredsubjectId((long) Integer.parseInt(a));
				dto.setSubjectId(newsub.getId());
				dto.setStatus("OPTIONAL");
				reqService.createreq(dto);
			}
		}
		rm.addAttribute("error",ActionStatus.CREATED);
		//khi thêm trường hợp mới thì vào thì qua trang index html thêm vào
		return "redirect:/admin/subject/list";
	}

	@GetMapping("/updatesubject/{id}")
	public String showupdatesubject(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		// lay thong tin mon hoc
		Subject sb = subjectRepository.getById(id);

		// lay tat ca truong cua subject

		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();

		model.addAttribute("type", ClassType.values());
		model.addAttribute("subject", sb);
		model.addAttribute("fields", currentUser.getUser().getFields().get(0).getId());
		model.addAttribute("subjectLevels", subjectLevels);

		return "subject/update";
	}

	/* @RequestParam(name = "id") */
	@PostMapping("/update/{id}")
	public String saveupdate(Model model, @ModelAttribute("subject") @Valid SubjectCreateDto dto, BindingResult result,
			@RequestParam(name = "id") Long id, RedirectAttributes rm) {

		subService.updatesubject(dto);
		rm.addAttribute("error",ActionStatus.UPDATED);
		return "redirect:/admin/subject/list";
	}

	@GetMapping("/hidesubject/{id}")
	public String hideSubject(@PathVariable("id") Long id, Model model,RedirectAttributes rm ) {
		subService.hideById(id);
		/* return "redirect:/subjects"; */
		rm.addAttribute("error",ActionStatus.DELETED);
		return "redirect:/admin/subject/list";
	}

	@GetMapping("/hiddenSubject")
	public String showHiddenSubject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,

			@RequestParam(name = "level", required = false) Long se,

			@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);
		Long fieldId = currentUser.getUser().getFields().get(0).getId();

		Page<SubjectDto> data = subService.getListPage(fieldId, se == null || se == 0 ? null : se, paging);
		
		List<RequiredSubject> listreRequiredSubjects = new ArrayList<>();

		for(SubjectDto a : data ) {
			listreRequiredSubjects = reqService.findListRequiredSubjectBySubjectId(a.getId());
			System.out.println("iddd"+a.getId());
			List<String> listRequiredSubject = new ArrayList<>();
			List<String> optionalRequiredSubjectList = new ArrayList<>();
			
			for(RequiredSubject a2: listreRequiredSubjects)
			{
				  if(a2.getStatus().equals("PASS"))
				  {
					  listRequiredSubject.add(a2.getRequiredsubject().getName());
				  }
				  else if(a2.getStatus().equals("OPTIONAL"))
				  {
					  optionalRequiredSubjectList.add(a2.getRequiredsubject().getName());
				  }		
			}
			//System.out.println("dataaaa;"+listRequiredSubject.toString());
			//System.out.println("dataaaa;"+optionalRequiredSubjectList.toString());
			a.setOptionalRequiredSuject(optionalRequiredSubjectList);
			a.setPassedSubjects(listRequiredSubject);
		}
		
		List<SubjectLevel> sblevel = sublvService.findAll();
		model.addAttribute("data", data);
		model.addAttribute("sblevels", sblevel);
		model.addAttribute("hiddenselectLevel", se == null ? 0 : se);
		return "subject/hiddensubject";
	}

	@GetMapping("/unhidesubject/{id}")
	public String ShowSubject(@PathVariable("id") Long id, Model model) {
		subService.showById(id);
		/* return "redirect:/subjects"; */
		return "redirect:/admin/subject/list";
	}

	@GetMapping("/detailsubject/{id}")
	public String detailsubject(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		
		// lay thong tin mon hoc
		Subject sb = subjectRepository.getById(id);
		
		//List<RequiredSubject> listreRequiredSubjects = reqService.findListRequiredSubjectBySubjectId(id);
		//List<RequiredSubject> req = reqService.findAll();
		
		List<String> listRequiredSubject = new ArrayList<>();
		List<String> optionalRequiredSubjectList = new ArrayList<>();
		List<RequiredSubject> listreRequiredSubjects = new ArrayList<>();
		
		for(RequiredSubject a2: listreRequiredSubjects)
		{
			listreRequiredSubjects = reqService.findListRequiredSubjectBySubjectId(a2.getId());
			  if(a2.getStatus().equals("PASS"))
			  {
				  listRequiredSubject.add(a2.getRequiredsubject().getName());
			  }
			  else if(a2.getStatus().equals("OPTIONAL"))
			  {
				  optionalRequiredSubjectList.add(a2.getRequiredsubject().getName());
			  }		
		}
		
		model.addAttribute("req", listreRequiredSubjects);

		model.addAttribute("type", ClassType.values());
		model.addAttribute("subject", sb);
		model.addAttribute("fields", currentUser.getUser().getFields().get(0).getId());
		//model.addAttribute("subjectLevels", subjectLevels);
		
		return "subject/detail";
	}

	@GetMapping("/listrequiredsubject")
	public String listreqsub(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<RequiredSubject> req = reqService.findAll();
		model.addAttribute("req", req);

		return "subject/reqsubject";
	}

	@GetMapping("/updatereq/{id}")
	public String showupdatereq(@PathVariable("id") Long id, Model model) {
		// lay thong tin reqsub
		RequiredSubject re = reqService.findById(id);
		// lay thong tin can update
		model.addAttribute("re", re);
		return "subject/updatereq";
	}

	@PostMapping("/updatereq/{id}")
	public String saveUpdatereq(Model model, @ModelAttribute("requiredsubject") @Valid RequiredSubjectDto dto,
			BindingResult result, @RequestParam(name = "id") Long id) {
		if (result.hasErrors()) {
			return "subject/updatereq";
		}
		reqService.update(dto);
		return "redirect:/admin/subject/listrequiredsubject";
	}

}