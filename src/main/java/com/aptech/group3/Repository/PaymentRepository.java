package com.aptech.group3.Repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Paymenttt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaymentRepository extends JpaRepository<Paymenttt, Long> {
    List<Paymenttt> findByStudentId(Long studentId);
    Page<Paymenttt> findAll(Pageable pageable);
}