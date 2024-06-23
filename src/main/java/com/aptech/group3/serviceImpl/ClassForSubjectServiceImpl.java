package com.aptech.group3.serviceImpl;


import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.ClassSubject;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectBasicDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassSubjectEditOneDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.RoomRegistedService;

import shared.BaseMethod;

@Service
public class ClassForSubjectServiceImpl implements ClassForSubjectService {
	@Autowired
	private ClassForSubjectRepository classRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private RoomRepository roomRepo;

	@Autowired
	private SemesterRepository semesterRepo;
	
	@Autowired
	private LessonSubjectService lesssonService;
	@Autowired
	private StudentClassRepository studentClassRepo;
	

	

	@Autowired
	private ModelMapper mapper;
	
	@Autowired 
	private RoomRegistedService roomRegistedService;
	
	public List<ClassForSubject> getClassSubjectsByTeacherId(Long teacherId) {
        return classRepository.findByTeacherId(teacherId);
    }
	//thanh thêm 23/06
	//thanh thêm   
	 public List<StudentClass> findStudentClassesByClassId(Long classId) {
	        return studentClassRepo.findByClassforSubjectId(classId);
	    }
	 public List<ClassForSubject> findClassesByName(String className) {
	        return classRepository.findByNameContainingIgnoreCase(className);
	    }
	
	public List<ClassForSubject> findAllBySemesterId(Long semesterId) {
	    return classRepository.lissClassBySemesterId(semesterId);
	}
	
	public Long getClassSubjectIdBySubjectId(Long subjectId) {
        List<ClassForSubject> classSubjects = classRepository.findBySubjectId(subjectId);
        if (!classSubjects.isEmpty()) {
            // Assuming you want to return the ID of the first class subject found
            return classSubjects.get(0).getId();
        } else {
            return null; // Or handle the case where no class subject is found based on your requirements
        }
    }
	public Long getClassSubjectIdByTeacherId(Long teacherId) {
        List<ClassForSubject> classSubjects = classRepository.findByTeacherId(teacherId);
        if (!classSubjects.isEmpty()) {
            return classSubjects.get(0).getId();
        } else {
            return null; // Or handle the case where no class subject is found based on your requirements
        }
    }
	
	
	
	
	public void delete(Long classId) {
		
		lesssonService.deleteLessonByClassId(classId);
		ClassForSubject data=	classRepository.findById(classId).orElse(null);
		
		classRepository.delete(data);
	}
	//new 
	
	public Page<ClassForSubject> getSubjectByFieldAndSemester(ClassSubject status, Long fieldId, Long semesterId,Long subjectId,Pageable pageable){
		Page<ClassForSubject> data =classRepository.findByFieldIdAndSubjectIdAndStatus(semesterId,fieldId,  subjectId,status,  pageable);
				
		return data;
	}
	
	public List<ClassForSubject> getListNoPage(ClassSubject status, Long fieldId, Long semesterId,Long subjectId){
		List<ClassForSubject> data= classRepository.getListByCondition(semesterId, fieldId, subjectId, status);
		return data;
	}
	
	public void updateClassStatus(ClassSubject status, List<Long> listId) {
		classRepository.updateStatue(status, listId);
	}
	
    public List<ClassForSubject> getClassesForToday(Long teacherId) {
        Date today = new Date();
        return classRepository.findAllByDateTodayAndUserId(today, teacherId);
    }

	public List<ClassForSubject> findByRegistrationDate(Date date)
	{
		return classRepository.findClasWhereDateBetweenRegistration(date);
	}
	
	
	public List<ClassForSubject> getByTeacherIdAndSemester(Long teacherId, Long semesterId){
		return classRepository.findByteacherIdAndSemeterId(teacherId, semesterId);
	}
	
	public List<TimeTableShowDto> getTeacherTimeTable(Long teacherId,Long semesterId, Date dateStart, Date dateEnd) {
		List<TimeTableShowDto>  data= classRepository.getBystartEndAndTeacherId(teacherId, semesterId, dateStart,dateEnd)
				.stream().map(e->{
					TimeTableShowDto dto = new 	TimeTableShowDto();
					dto.setClass_id(e.getId());
					dto.setEndSlot(e.getSlotEnd());
					dto.setName(e.getName());
					dto.setRoom(e.getRoom().getName());
					dto.setStartSlot(e.getSlotStart());
					dto.setWeekDay(e.getWeekDay());
					return dto;
				}).toList();
		
		return data;
		
	}
	

	   public List<ClassForSubject> findBySemesterIdAndFieldId(Long semesterId,Long fieldId) {
	        return classRepository.findBySemeterIdAnd(semesterId, fieldId);
	    }
	   
	   public List<ClassForSubject> listClassBySemesterId(Long semesterId) {
	        return classRepository.lissClassBySemesterId(semesterId);
	    }
	   
	   
	   public List<ClassForSubject> findAll() {
	        return classRepository.findAll();
	    }
	   
	
	    public List<ClassForSubject> getClassSubjects(Long classId) {
	        List<ClassForSubject> classForSubjects = classRepository.findBySubject_Id(classId);
	        return classForSubjects;
	    }
	    
	    		
	
	public List<ClassForSubject> getAllByfieldAndSemester( Long semesterId, Long fieldId){
		return classRepository.findBySemeter_IdAndSubject_field_Id( semesterId, fieldId);
	}
	
	public boolean checkType(Long id){
		boolean result=false;
		ClassForSubject data=classRepository.findById(id).orElse(null);
		List<ClassForSubject> check= classRepository.findByName(data.getName());
		
		if (check.size()>1 ) {
			result=true;
		}
		return result;
	}
	
	public ClassSubjectEditOneDto getEditDto(Long id) {
		
		ClassSubjectEditOneDto data = classRepository.findById(id)
			    .map(e -> {
			    	ClassSubjectEditOneDto dto=	mapper.map(e, ClassSubjectEditOneDto.class);
			    	dto.setRoom_id(e.getRoom().getId());
			    	dto.setSemeter_id(e.getSemeter().getId());
			    	dto.setTeacher_id(e.getTeacher().getId());
			    	dto.setSubject_id(e.getSubject().getId());
			    	return dto;
			    	}).orElse(null);
		
		return data;
	}
	
	public void Edit(ClassSubjectEditOneDto dto) {
		ClassForSubject data =mapper.map(dto, ClassForSubject.class);
		semesterRepo.findById(dto.getSemeter_id()).ifPresent(data::setSemeter);
		subjectRepo.findById(dto.getSubject_id()).ifPresent(data::setSubject);
		userRepo.findById(dto.getTeacher_id()).ifPresent(data::setTeacher);
		roomRepo.findById(dto.getRoom_id()).ifPresent(data::setRoom);
		
		classRepository.save(data);
		
	}


     // HIEN
	public List<ClassForSubject> findByTeacherId(Long teacherID)
	{
       return classRepository.findByTeacherId(teacherID);
	}

	
	
	public ClassForSubject findById(Long id) {
		return classRepository.findById(id).orElse(null);
	}
	
	public List<ClassForSubjectDto> findBySubjectId(Long id) {
		List<ClassForSubject> classss = classRepository.findBySubjectId(id);
		return mapper.map(classss,  new TypeToken<List<ClassForSubjectDto>>() {}.getType());
	}
	
	public List<ClassForSubjectDto> findBySubjectIdAndDate(Long id,Date date) {
		List<ClassForSubject> classss = classRepository.findBySubjectIdAndDateBetweenRegistration(id, date);
		return mapper.map(classss,  new TypeToken<List<ClassForSubjectDto>>() {}.getType());
	}
	
	
	
	
	public ClassForSubject findByClassId(int id) {
		return classRepository.findById(id);
	}
	
	public void create(ClassSubjectCreateDto data) {
		try {
			ClassForSubject sclass = mapper.map(data, ClassForSubject.class);
			userRepo.findById(data.getTeacher_id()).ifPresent(sclass::setTeacher);
			subjectRepo.findById(data.getSubject_id()).ifPresent(sclass::setSubject);
			roomRepo.findById(data.getRoom_id()).ifPresent(sclass::setRoom);
			semesterRepo.findById(data.getSemeter_id()).ifPresent(sclass::setSemeter);

			sclass.setDateStart(BaseMethod.convertDate(data.getDate_start()));
			sclass.setDateEnd(BaseMethod.convertDate(data.getDate_end()));
			sclass.setStatus(ClassSubject.CREATED);
			sclass.setMinQuantity((data.getQuantity()*30)/100);
			ClassForSubject recive = classRepository.save(sclass);
			recive.setName(genderClassName(recive.getSubject().getName(),recive.getId().intValue()));
			
			ClassForSubject	finalResult=classRepository.save(recive);
			lesssonService.create(finalResult);
			roomRegistedService.create(finalResult.getId(), finalResult.getRoom().getId());
		} catch (Exception ex) {
			throw ex;
		}

	}

	public void createAll(ClassSubjectAllDto data) {

		ClassForSubject theoClass = mapper.map(data.getTheory(), ClassForSubject.class);
		ClassForSubject ActionClass = mapper.map(data.getAction(), ClassForSubject.class);

		theoClass = mapForAll(theoClass, true, data);
		ClassForSubject theoResult=	classRepository.save(theoClass);
		
		 theoResult.setName(genderClassName(theoResult.getSubject().getName(),
		  theoResult.getId().intValue()));
		
		
		  ClassForSubject finalTheo= classRepository.save(theoResult);
		  lesssonService.create(finalTheo);
		  roomRegistedService.create(finalTheo.getId(), finalTheo.getRoom().getId());

		ActionClass  = mapForAll(ActionClass, false, data);
		ClassForSubject actionResult= classRepository.save(ActionClass);
		
		  actionResult.setName(genderClassName(actionResult.getSubject().getName(),
		  actionResult.getId().intValue()));
		 
		
		  ClassForSubject finalAction=classRepository.save(actionResult);
		  finalAction.setName(finalTheo.getName()); classRepository.save(finalAction);
		 
		lesssonService.create(finalAction); 
		roomRegistedService.create(finalAction.getId(), finalAction.getRoom().getId());
	
	}
	

	private String genderClassName(String subject, int id) {
		//get 2 start digit of year
		Year currentYear = Year.now();
		String lastTwoDigits = currentYear.format(DateTimeFormatter.ofPattern("yy"));
		// get start text of name of subject
		String[] words = subject.split(" ");
		StringBuilder startTextBuilder = new StringBuilder();
		for (String word : words) {
			if (!word.isEmpty()) {
				startTextBuilder.append(word.charAt(0));
			}
		}
		String startText = startTextBuilder.toString();
		String enNum="";
		//gen number follow id
		if(id<1000) {
			enNum= String.valueOf(1000+ id);
		}
		if(id>=1000) {
			enNum= String.valueOf( id);
		}

		return startText + lastTwoDigits + enNum;
	}

	private ClassForSubject mapForAll(ClassForSubject dataClass, boolean isTheory, ClassSubjectAllDto data) {

		if (isTheory) {
			dataClass.setDateStart(BaseMethod.convertDate(data.getTheory().getDate_start()));
			dataClass.setDateEnd(BaseMethod.convertDate(data.getTheory().getDate_end()));
			userRepo.findById(data.getTheory().getTeacher_id()).ifPresent(dataClass::setTeacher);
			roomRepo.findById(data.getTheory().getRoom_id()).ifPresent(dataClass::setRoom);
			dataClass.setDescription("Theory");
		} else {
			dataClass.setDateStart(BaseMethod.convertDate(data.getAction().getDate_start()));
			dataClass.setDateEnd(BaseMethod.convertDate(data.getAction().getDate_end()));
			userRepo.findById(data.getAction().getTeacher_id()).ifPresent(dataClass::setTeacher);
			roomRepo.findById(data.getAction().getRoom_id()).ifPresent(dataClass::setRoom);
			dataClass.setDescription("Action");
		}
		dataClass.setQuantity(data.getQuantity());
		dataClass.setName(data.getName());
		subjectRepo.findById(data.getSubject_id()).ifPresent(dataClass::setSubject);
		semesterRepo.findById(data.getSemeter_id()).ifPresent(dataClass::setSemeter);
		dataClass.setStatus(ClassSubject.CREATED);
		return dataClass;
	}

	

}
