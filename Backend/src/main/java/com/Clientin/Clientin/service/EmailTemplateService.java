package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.EmailTemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateService {
    
    // Basic CRUD operations
    EmailTemplateDTO create(EmailTemplateDTO dto);
    Optional<EmailTemplateDTO> findById(String id);
    Page<EmailTemplateDTO> findAll(Pageable pageable);
    List<EmailTemplateDTO> findAll();
    EmailTemplateDTO update(String id, EmailTemplateDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<EmailTemplateDTO> search(Specification<EmailTemplate> spec, Pageable pageable);
    EmailTemplateDTO partialUpdate(String id, EmailTemplateDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<EmailTemplateDTO> bulkCreate(List<EmailTemplateDTO> dtos);
    void bulkDelete(List<String> ids);
}