package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.BadgeDTO;
import com.Clientin.Clientin.entity.Badge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BadgeService {
    
    // Basic CRUD operations
    BadgeDTO create(BadgeDTO dto);
    Optional<BadgeDTO> findById(String id);
    Page<BadgeDTO> findAll(Pageable pageable);
    List<BadgeDTO> findAll();
    BadgeDTO update(String id, BadgeDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<BadgeDTO> search(Specification<Badge> spec, Pageable pageable);
    BadgeDTO partialUpdate(String id, BadgeDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<BadgeDTO> bulkCreate(List<BadgeDTO> dtos);
    void bulkDelete(List<String> ids);
}