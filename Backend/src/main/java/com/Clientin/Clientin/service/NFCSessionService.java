package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.NFCSessionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface NFCSessionService {
    
    // Basic CRUD operations
    NFCSessionDTO create(NFCSessionDTO dto);
    Optional<NFCSessionDTO> findById(String id);
    Page<NFCSessionDTO> findAll(Pageable pageable);
    List<NFCSessionDTO> findAll();
    NFCSessionDTO update(String id, NFCSessionDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<NFCSessionDTO> search(Specification<NFCSession> spec, Pageable pageable);
    NFCSessionDTO partialUpdate(String id, NFCSessionDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<NFCSessionDTO> bulkCreate(List<NFCSessionDTO> dtos);
    void bulkDelete(List<String> ids);
}