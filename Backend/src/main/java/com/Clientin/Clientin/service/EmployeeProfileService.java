package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.EmployeeProfileDTO;
import com.Clientin.Clientin.entity.EmployeeProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileService {
    
    // Basic CRUD operations
    EmployeeProfileDTO create(EmployeeProfileDTO dto);
    Optional<EmployeeProfileDTO> findById(String id);
    Page<EmployeeProfileDTO> findAll(Pageable pageable);
    List<EmployeeProfileDTO> findAll();
    EmployeeProfileDTO update(String id, EmployeeProfileDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<EmployeeProfileDTO> search(Specification<EmployeeProfile> spec, Pageable pageable);
    EmployeeProfileDTO partialUpdate(String id, EmployeeProfileDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<EmployeeProfileDTO> bulkCreate(List<EmployeeProfileDTO> dtos);
    void bulkDelete(List<String> ids);
}