package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.SubscriptionDTO;
import com.Clientin.Clientin.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    
    // Basic CRUD operations
    SubscriptionDTO create(SubscriptionDTO dto);
    Optional<SubscriptionDTO> findById(String id);
    Page<SubscriptionDTO> findAll(Pageable pageable);
    List<SubscriptionDTO> findAll();
    SubscriptionDTO update(String id, SubscriptionDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<SubscriptionDTO> search(Specification<Subscription> spec, Pageable pageable);
    SubscriptionDTO partialUpdate(String id, SubscriptionDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<SubscriptionDTO> bulkCreate(List<SubscriptionDTO> dtos);
    void bulkDelete(List<String> ids);
}