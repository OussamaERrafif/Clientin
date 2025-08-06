package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.EClientDTO;
import com.Clientin.Clientin.entity.EClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EClientService {
    
    // Basic CRUD operations
    EClientDTO create(EClientDTO dto);
    Optional<EClientDTO> findById(String id);
    Page<EClientDTO> findAll(Pageable pageable);
    List<EClientDTO> findAll();
    EClientDTO update(String id, EClientDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<EClientDTO> search(Specification<EClient> spec, Pageable pageable);
    EClientDTO partialUpdate(String id, EClientDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<EClientDTO> bulkCreate(List<EClientDTO> dtos);
    void bulkDelete(List<String> ids);
}