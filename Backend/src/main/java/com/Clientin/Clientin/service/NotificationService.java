package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    
    // Basic CRUD operations
    NotificationDTO create(NotificationDTO dto);
    Optional<NotificationDTO> findById(String id);
    Page<NotificationDTO> findAll(Pageable pageable);
    List<NotificationDTO> findAll();
    NotificationDTO update(String id, NotificationDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<NotificationDTO> search(Specification<Notification> spec, Pageable pageable);
    NotificationDTO partialUpdate(String id, NotificationDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<NotificationDTO> bulkCreate(List<NotificationDTO> dtos);
    void bulkDelete(List<String> ids);
}