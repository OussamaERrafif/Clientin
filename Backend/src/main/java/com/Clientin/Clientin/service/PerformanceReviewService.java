package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.PerformanceReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PerformanceReviewService {
    
    // Basic CRUD operations
    PerformanceReviewDTO create(PerformanceReviewDTO dto);
    Optional<PerformanceReviewDTO> findById(String id);
    Page<PerformanceReviewDTO> findAll(Pageable pageable);
    List<PerformanceReviewDTO> findAll();
    PerformanceReviewDTO update(String id, PerformanceReviewDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<PerformanceReviewDTO> search(Specification<PerformanceReview> spec, Pageable pageable);
    PerformanceReviewDTO partialUpdate(String id, PerformanceReviewDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<PerformanceReviewDTO> bulkCreate(List<PerformanceReviewDTO> dtos);
    void bulkDelete(List<String> ids);
}