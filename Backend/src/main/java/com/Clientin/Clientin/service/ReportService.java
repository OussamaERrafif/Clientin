package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.ReportDTO;
import com.Clientin.Clientin.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    
    // Basic CRUD operations
    ReportDTO create(ReportDTO dto);
    Optional<ReportDTO> findById(String id);
    Page<ReportDTO> findAll(Pageable pageable);
    List<ReportDTO> findAll();
    ReportDTO update(String id, ReportDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<ReportDTO> search(Specification<Report> spec, Pageable pageable);
    ReportDTO partialUpdate(String id, ReportDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<ReportDTO> bulkCreate(List<ReportDTO> dtos);
    void bulkDelete(List<String> ids);
}