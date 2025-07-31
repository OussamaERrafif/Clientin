package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.InsightDTO;
import com.Clientin.Clientin.entity.Insight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface InsightService {
    
    // Basic CRUD operations
    InsightDTO create(InsightDTO dto);
    Optional<InsightDTO> findById(String id);
    Page<InsightDTO> findAll(Pageable pageable);
    List<InsightDTO> findAll();
    InsightDTO update(String id, InsightDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<InsightDTO> search(Specification<Insight> spec, Pageable pageable);
    InsightDTO partialUpdate(String id, InsightDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<InsightDTO> bulkCreate(List<InsightDTO> dtos);
    void bulkDelete(List<String> ids);
}