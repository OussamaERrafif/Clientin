package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.RewardDTO;
import com.Clientin.Clientin.entity.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface RewardService {
    
    // Basic CRUD operations
    RewardDTO create(RewardDTO dto);
    Optional<RewardDTO> findById(String id);
    Page<RewardDTO> findAll(Pageable pageable);
    List<RewardDTO> findAll();
    RewardDTO update(String id, RewardDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<RewardDTO> search(Specification<Reward> spec, Pageable pageable);
    RewardDTO partialUpdate(String id, RewardDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<RewardDTO> bulkCreate(List<RewardDTO> dtos);
    void bulkDelete(List<String> ids);
}