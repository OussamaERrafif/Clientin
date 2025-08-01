package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AuditLogService {
    
    // Basic CRUD operations
    AuditLogDTO create(AuditLogDTO dto);
    Optional<AuditLogDTO> findById(String id);
    Page<AuditLogDTO> findAll(Pageable pageable);
    List<AuditLogDTO> findAll();
    AuditLogDTO update(String id, AuditLogDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<AuditLogDTO> search(Specification<AuditLog> spec, Pageable pageable);
    AuditLogDTO partialUpdate(String id, AuditLogDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<AuditLogDTO> bulkCreate(List<AuditLogDTO> dtos);
    void bulkDelete(List<String> ids);
}