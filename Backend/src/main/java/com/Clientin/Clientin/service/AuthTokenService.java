package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AuthTokenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AuthTokenService {
    
    // Basic CRUD operations
    AuthTokenDTO create(AuthTokenDTO dto);
    Optional<AuthTokenDTO> findById(String id);
    Page<AuthTokenDTO> findAll(Pageable pageable);
    List<AuthTokenDTO> findAll();
    AuthTokenDTO update(String id, AuthTokenDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<AuthTokenDTO> search(Specification<AuthToken> spec, Pageable pageable);
    AuthTokenDTO partialUpdate(String id, AuthTokenDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<AuthTokenDTO> bulkCreate(List<AuthTokenDTO> dtos);
    void bulkDelete(List<String> ids);
}