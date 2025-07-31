package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.NotificationPreferenceDTO;
import com.Clientin.Clientin.entity.NotificationPreference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface NotificationPreferenceService {
    
    // Basic CRUD operations
    NotificationPreferenceDTO create(NotificationPreferenceDTO dto);
    Optional<NotificationPreferenceDTO> findById(String id);
    Page<NotificationPreferenceDTO> findAll(Pageable pageable);
    List<NotificationPreferenceDTO> findAll();
    NotificationPreferenceDTO update(String id, NotificationPreferenceDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<NotificationPreferenceDTO> search(Specification<NotificationPreference> spec, Pageable pageable);
    NotificationPreferenceDTO partialUpdate(String id, NotificationPreferenceDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<NotificationPreferenceDTO> bulkCreate(List<NotificationPreferenceDTO> dtos);
    void bulkDelete(List<String> ids);
}