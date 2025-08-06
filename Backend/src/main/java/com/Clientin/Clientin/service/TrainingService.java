package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.TrainingDTO;
import com.Clientin.Clientin.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    
    // Basic CRUD operations
    TrainingDTO create(TrainingDTO dto);
    Optional<TrainingDTO> findById(String id);
    Page<TrainingDTO> findAll(Pageable pageable);
    List<TrainingDTO> findAll();
    TrainingDTO update(String id, TrainingDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<TrainingDTO> search(Specification<Training> spec, Pageable pageable);
    TrainingDTO partialUpdate(String id, TrainingDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<TrainingDTO> bulkCreate(List<TrainingDTO> dtos);
    void bulkDelete(List<String> ids);
}