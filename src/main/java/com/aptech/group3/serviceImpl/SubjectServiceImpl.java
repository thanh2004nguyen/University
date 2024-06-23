package com.aptech.group3.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.RequiredSubjectRepository;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.SubjectService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private SubjectLevelRepository LevelRepo;

	@Autowired
	private RequiredSubjectRepository reqsubRepo;

	@Autowired
	private FiledRepository fieldRepo;

	@Autowired
	private ModelMapper mapper;

	public List<Subject> findPassesSubject(User student)
	{
		return subjectRepo.findPassedSubject(student);
	}
	
	public Subject findByName(String name)
	{
		return subjectRepo.findByName(name);
	}

	public List<Subject> searchSubject(String name, Integer fieldId, Integer levelId) {
		List<Subject> data = subjectRepo.findByMultipleCriteria(name, levelId, fieldId);
		return data;
	}

	public List<Subject> getByField(Long id) {
		return subjectRepo.findByFieldId(id);
	}
	
	public List<Subject> getByFieldAndLevel(Long id,Long fieldId) {
		return subjectRepo.findBySubjectlevelIdAndFieldId(id, fieldId);
	}
	

	public List<Subject> findAll() {
		return subjectRepo.findAll();
	}

	public int getCredit(int id) {
		return subjectRepo.getCreditById(id);
	}

	public List<SubjectLevel> listSubjectLevel() {
		return LevelRepo.findAll();
	}

	public List<Subject> findBySubjectName(String name) {
		return subjectRepo.findByNameContainingIgnoreCase(name);
	}

	public Optional<Subject> findbyId(Long id) {
		return subjectRepo.findById(id);
	}

	public List<Subject> findByLevel(Long levelId) {
		return subjectRepo.findBySubjectlevelId(levelId);
	}

	public Subject saveSubject(Subject sub) {
		return subjectRepo.save(sub);
	}

	public List<Subject> listSubject() {
		return subjectRepo.findAll();
	}

	public void updateSubject(Subject sub) {
		Long RoomId = sub.getId();
		if (subjectRepo.existsById(RoomId)) {
			subjectRepo.save(sub);
		}

	}

	public List<SubjectDto> findByStudent(User student, Long field) {
		List<Subject> listS = subjectRepo.findSubjectsForStudent(student, field);
		return mapper.map(listS, new TypeToken<List<SubjectDto>>() {
		}.getType());
	}
	
	public List<SubjectDto> findByStudentMoNeedRequiredSubjectCondition(Long field) {
		List<Subject> listS = subjectRepo.findSubjectsForStudentAndNoNeedRequiredSubject(field);
		return mapper.map(listS, new TypeToken<List<SubjectDto>>() {
		}.getType());
	}
	
	
	
	// DU
		public List<RequiredSubject> listReq() {
			return reqsubRepo.findAll();
		}

		
		
		public Subject create(SubjectCreateDto data) {
			
			Subject subject = new Subject();
			subject.setName(data.getName());
			subject.setCredit(data.getCredit());
			subject.setType(data.getType());
			subject.setCreditAction(data.getCreditAction()); 
			
			fieldRepo.findById(data.getField_id()).ifPresent(subject::setField);
			
		
			LevelRepo.findById(data.getSubjectlevel_id()).ifPresent(subject::setSubjectlevel);
		
			Subject newsub =subjectRepo.save(subject);
			return newsub;
		}
		
		
		    public void saveSubjectWithRequiredSubjects(Subject subject, Set<RequiredSubject> requiredSubjects) {
		        subject = subjectRepo.save(subject);
		        for (RequiredSubject requiredSubject : requiredSubjects) {
		            requiredSubject.setSubject(subject);
		            reqsubRepo.save(requiredSubject);
		        }
		    }

		/*
		 * public void createrequired(RequiredSubjectDto reqdata) {
		 * 
		 * RequiredSubject req = new RequiredSubject();
		 * 
		 * reqsubRepo.findById(0); reqsubRepo.findById(0); reqsubRepo.save(req); }
		 */
		 public void updatesubject(SubjectCreateDto dto) {
			 Optional <Subject> subOptional = subjectRepo.findById(dto.getId());
			 
			 if(subOptional.isPresent()) {
				 Subject sub = subOptional.get();
				 sub.setName(dto.getName());
				 sub.setType(dto.getType());
				 sub.setCredit(dto.getCredit());
					/* sub.setCreditAction(dto.getCreditAction()); */
				 subjectRepo.save(sub);
				
			 }else {
					throw new UsernameNotFoundException("Subject not found!");  // Xử lý khi không tìm thấy người dùng
				}
			 
			
		 }
		 
		 public List<Subject> getByFieldIDAndLevel(Long FieldId, Long level) {
				return subjectRepo.getListSubjectByFieldAndLevel(FieldId, level);
			}
		 
		 
		 public void hideById(Long id) {
		        subjectRepo.findById(id).ifPresent(subject -> {
		            subject.setHidden(true);
		            subjectRepo.save(subject);
		        });
		    }
		 
		 
		 public void showById(Long id) {
		        subjectRepo.findById(id).ifPresent(subject -> {
		            subject.setHidden(false);
		            subjectRepo.save(subject);
		        });
		    }

		 
		   public Page<SubjectDto> getListPage(Long fieldId, Long levelId, Pageable pageable) {
		        Page<Subject> subjectPage = subjectRepo.findByFieldIdAndSubjectlevelId(fieldId, levelId, pageable);

		        List<SubjectDto> subjectDtoList = mapper.map(subjectPage.getContent(), new TypeToken<List<SubjectDto>>() {}.getType());

		        return new PageImpl<>(subjectDtoList, pageable, subjectPage.getTotalElements());
		    }
		   
		   public boolean CheckNameExists(String name) {
			   Subject subject = subjectRepo.findByName(name);
			   return subject != null;
		   }
		   
		   public boolean existsByName(String name) {
		        return subjectRepo.existsByName(name);
		    }
		   
		    
}
