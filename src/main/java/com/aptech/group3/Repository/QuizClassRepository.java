package com.aptech.group3.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.aptech.group3.entity.QuizClass;




public interface QuizClassRepository extends JpaRepository<QuizClass, Long> {
	
                 List<QuizClass> findByClassForSubjectId(Long id);
        
                 
           
}
