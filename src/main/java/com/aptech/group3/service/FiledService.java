package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.FieldDto;
import com.aptech.group3.entity.Field;

public interface FiledService {
    List<Field> getAllFields();
    public Field getFieldById(Long id);
    List<Field> findByUserId(Long userId);
    public List<Field> getFieldsByIds(List<Long> fieldIds);
}