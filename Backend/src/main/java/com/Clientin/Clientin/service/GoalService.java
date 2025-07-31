package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.GoalDTO;
import com.Clientin.Clientin.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    
    // Basic CRUD operations
    GoalDTO create(GoalDTO dto);
    Optional<GoalDTO> findById(String id);
    Page<GoalDTO> findAll(Pageable pageable);
    List<GoalDTO> findAll();
    GoalDTO update(String id, GoalDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<GoalDTO> search(Specification<Goal> spec, Pageable pageable);
    GoalDTO partialUpdate(String id, GoalDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<GoalDTO> bulkCreate(List<GoalDTO> dtos);
    void bulkDelete(List<String> ids);
}