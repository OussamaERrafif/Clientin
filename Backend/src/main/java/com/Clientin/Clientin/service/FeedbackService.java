package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.FeedbackDTO;
import com.Clientin.Clientin.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    
    // Basic CRUD operations
    FeedbackDTO create(FeedbackDTO dto);
    Optional<FeedbackDTO> findById(String id);
    Page<FeedbackDTO> findAll(Pageable pageable);
    List<FeedbackDTO> findAll();
    FeedbackDTO update(String id, FeedbackDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<FeedbackDTO> search(Specification<Feedback> spec, Pageable pageable);
    FeedbackDTO partialUpdate(String id, FeedbackDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<FeedbackDTO> bulkCreate(List<FeedbackDTO> dtos);
    void bulkDelete(List<String> ids);
}