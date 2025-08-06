package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AbsenceDTO;
import com.Clientin.Clientin.entity.Absence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceService {
    
    // Basic CRUD operations
    AbsenceDTO create(AbsenceDTO dto);
    Optional<AbsenceDTO> findById(String id);
    Page<AbsenceDTO> findAll(Pageable pageable);
    List<AbsenceDTO> findAll();
    AbsenceDTO update(String id, AbsenceDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<AbsenceDTO> search(Specification<Absence> spec, Pageable pageable);
    AbsenceDTO partialUpdate(String id, AbsenceDTO dto);
    boolean exists(String id);
    
    // Business-specific operations
    AbsenceDTO approve(String id, String approverId);
    AbsenceDTO reject(String id, String approverId, String reason);
    List<AbsenceDTO> findByEmployeeId(String employeeId);
    List<AbsenceDTO> findPendingApprovals();
    List<AbsenceDTO> findByDateRange(LocalDate startDate, LocalDate endDate);
    int calculateAbsenceDays(LocalDate startDate, LocalDate endDate);
    boolean hasConflictingAbsence(String employeeId, LocalDate startDate, LocalDate endDate);
    
    // Bulk operations
    List<AbsenceDTO> bulkCreate(List<AbsenceDTO> dtos);
    void bulkDelete(List<String> ids);
    List<AbsenceDTO> bulkApprove(List<String> ids, String approverId);
}