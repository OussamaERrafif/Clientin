package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AppSettingsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AppSettingsService {
    
    // Basic CRUD operations
    AppSettingsDTO create(AppSettingsDTO dto);
    Optional<AppSettingsDTO> findById(String id);
    Page<AppSettingsDTO> findAll(Pageable pageable);
    List<AppSettingsDTO> findAll();
    AppSettingsDTO update(String id, AppSettingsDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<AppSettingsDTO> search(Specification<AppSettings> spec, Pageable pageable);
    AppSettingsDTO partialUpdate(String id, AppSettingsDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<AppSettingsDTO> bulkCreate(List<AppSettingsDTO> dtos);
    void bulkDelete(List<String> ids);
}