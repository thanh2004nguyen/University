package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	public List<Holiday> findByYear(int year);


	public Page<Holiday> findByYear(int year, Pageable pageable);

	@Query("SELECT h FROM Holiday h WHERE h.classSubject IS NULL OR h.classSubject.id = :classId "
			+ " AND h.year= :year")
	public List<Holiday> getByclassSubjectOrNull(Long classId, int year);

}
