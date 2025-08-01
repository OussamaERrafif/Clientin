package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AnalyticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AnalyticsService {
    
    // Basic CRUD operations
    AnalyticsDTO create(AnalyticsDTO dto);
    Optional<AnalyticsDTO> findById(String id);
    Page<AnalyticsDTO> findAll(Pageable pageable);
    List<AnalyticsDTO> findAll();
    AnalyticsDTO update(String id, AnalyticsDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<AnalyticsDTO> search(Specification<Analytics> spec, Pageable pageable);
    AnalyticsDTO partialUpdate(String id, AnalyticsDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<AnalyticsDTO> bulkCreate(List<AnalyticsDTO> dtos);
    void bulkDelete(List<String> ids);
}