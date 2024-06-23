package com.aptech.group3.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
    @Query("SELECT n FROM News n ORDER BY n.createDate DESC")
    List<News> selectTopNew();
	
}