package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.PasswordResetTokenDTO;
import com.Clientin.Clientin.entity.PasswordResetToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenService {
    
    // Basic CRUD operations
    PasswordResetTokenDTO create(PasswordResetTokenDTO dto);
    Optional<PasswordResetTokenDTO> findById(String id);
    Page<PasswordResetTokenDTO> findAll(Pageable pageable);
    List<PasswordResetTokenDTO> findAll();
    PasswordResetTokenDTO update(String id, PasswordResetTokenDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<PasswordResetTokenDTO> search(Specification<PasswordResetToken> spec, Pageable pageable);
    PasswordResetTokenDTO partialUpdate(String id, PasswordResetTokenDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<PasswordResetTokenDTO> bulkCreate(List<PasswordResetTokenDTO> dtos);
    void bulkDelete(List<String> ids);
}