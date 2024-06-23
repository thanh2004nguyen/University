package com.aptech.group3.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Semeter;


public interface SemesterRepository extends JpaRepository<Semeter, Long> {
    
	public Semeter findById(int id);
	
	public Semeter findByYearAndName(int year, int name);
    
	@Query("SELECT s FROM semeter s where s.year= :year and s.name= :name")
    public Semeter currentSemester( int year, int name);
}
