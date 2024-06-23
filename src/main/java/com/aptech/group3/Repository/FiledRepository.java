package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Field;

public interface FiledRepository extends JpaRepository<Field, Long> {
	List<Field> findByUsersId(Long userId);
}
