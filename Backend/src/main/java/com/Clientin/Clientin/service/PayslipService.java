package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.PayslipDTO;
import com.Clientin.Clientin.entity.Payslip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PayslipService {
    
    // Basic CRUD operations
    PayslipDTO create(PayslipDTO dto);
    Optional<PayslipDTO> findById(String id);
    Page<PayslipDTO> findAll(Pageable pageable);
    List<PayslipDTO> findAll();
    PayslipDTO update(String id, PayslipDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<PayslipDTO> search(Specification<Payslip> spec, Pageable pageable);
    PayslipDTO partialUpdate(String id, PayslipDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<PayslipDTO> bulkCreate(List<PayslipDTO> dtos);
    void bulkDelete(List<String> ids);
}