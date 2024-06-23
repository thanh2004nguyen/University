package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.entity.Paymenttt;


public interface PaymentService {
    Optional<Paymenttt> findById(Long id);
    List<Paymenttt> findByStudentId(Long studentId);
    Page<Paymenttt> findAll(Pageable pageable);
    void save(Paymenttt payment);
    
    
}