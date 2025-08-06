package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.NFCDeviceDTO;
import com.Clientin.Clientin.entity.NFCDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface NFCDeviceService {
    
    // Basic CRUD operations
    NFCDeviceDTO create(NFCDeviceDTO dto);
    Optional<NFCDeviceDTO> findById(String id);
    Page<NFCDeviceDTO> findAll(Pageable pageable);
    List<NFCDeviceDTO> findAll();
    NFCDeviceDTO update(String id, NFCDeviceDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<NFCDeviceDTO> search(Specification<NFCDevice> spec, Pageable pageable);
    NFCDeviceDTO partialUpdate(String id, NFCDeviceDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<NFCDeviceDTO> bulkCreate(List<NFCDeviceDTO> dtos);
    void bulkDelete(List<String> ids);
}