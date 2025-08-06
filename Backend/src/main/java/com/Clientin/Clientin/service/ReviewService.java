package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.ReviewDTO;
import com.Clientin.Clientin.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    
    // Basic CRUD operations
    ReviewDTO create(ReviewDTO dto);
    Optional<ReviewDTO> findById(String id);
    Page<ReviewDTO> findAll(Pageable pageable);
    List<ReviewDTO> findAll();
    ReviewDTO update(String id, ReviewDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<ReviewDTO> search(Specification<Review> spec, Pageable pageable);
    ReviewDTO partialUpdate(String id, ReviewDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<ReviewDTO> bulkCreate(List<ReviewDTO> dtos);
    void bulkDelete(List<String> ids);
}