package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.FileUploadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface FileUploadService {
    
    // Basic CRUD operations
    FileUploadDTO create(FileUploadDTO dto);
    Optional<FileUploadDTO> findById(String id);
    Page<FileUploadDTO> findAll(Pageable pageable);
    List<FileUploadDTO> findAll();
    FileUploadDTO update(String id, FileUploadDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<FileUploadDTO> search(Specification<FileUpload> spec, Pageable pageable);
    FileUploadDTO partialUpdate(String id, FileUploadDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<FileUploadDTO> bulkCreate(List<FileUploadDTO> dtos);
    void bulkDelete(List<String> ids);
}