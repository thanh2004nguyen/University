package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.TeacherSubject;
import com.aptech.group3.entity.User;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {

    @Query("SELECT t.teacher FROM teacher_subject t WHERE t.subject.id = :id AND t.teacher.id NOT IN (SELECT r.teacher.id FROM teacher_registed r " +
           "WHERE r.class_registed.slotStart <= :start AND r.class_registed.slotEnd >= :start  AND r.class_registed.weekDay = :weekday " +
           "AND r.class_registed.dateStart >= :dstart AND r.class_registed.dateEnd <= :dend)")
    public List<User> getAvailableTeacher(int id, int weekday, int start, Date dstart, Date dend);
}

