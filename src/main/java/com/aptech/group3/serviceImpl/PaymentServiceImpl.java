package com.aptech.group3.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.aptech.group3.Repository.PaymentRepository;
import com.aptech.group3.entity.News;
import com.aptech.group3.entity.Paymenttt;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    public PaymentRepository repo;

    public Optional<Paymenttt> findById(Long id) {
        return repo.findById(id);
    }
    @Override
    public List<Paymenttt> findByStudentId(Long studentId) {
        return repo.findByStudentId(studentId);
    }
    @Override
    public Page<Paymenttt> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
   
    @Override 
    public void save(Paymenttt payment)
    {
    	 repo.save(payment);
    }
    
}