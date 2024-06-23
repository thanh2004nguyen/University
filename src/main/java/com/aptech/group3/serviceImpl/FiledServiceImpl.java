package com.aptech.group3.serviceImpl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.FieldDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.service.FiledService;

@Service
public class FiledServiceImpl implements FiledService {

    @Autowired
    private FiledRepository fieldRepository;

    @Override
    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }
    public Field getFieldById(Long id) {
        return fieldRepository.findById(id).orElse(null);
    }
    @Override
    public List<Field> findByUserId(Long userId) {
        return fieldRepository.findByUsersId(userId);
    }
    public List<Field> getFieldsByIds(List<Long> fieldIds) {
        return fieldRepository.findAllById(fieldIds);
    }
   
}