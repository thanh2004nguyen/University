package com.aptech.group3.Dto;

import java.util.List;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.MarkSubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkSubjectShowApiDto {
    private MarkSubject markSubject;
    private List<ClassForSubject> classSubjects;
    private List<LessonSubject> lesson;

    // Constructors, getters, setters
}