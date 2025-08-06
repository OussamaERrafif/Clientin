package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.ContractDTO;
import com.Clientin.Clientin.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    
    // Basic CRUD operations
    ContractDTO create(ContractDTO dto);
    Optional<ContractDTO> findById(String id);
    Page<ContractDTO> findAll(Pageable pageable);
    List<ContractDTO> findAll();
    ContractDTO update(String id, ContractDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<ContractDTO> search(Specification<Contract> spec, Pageable pageable);
    ContractDTO partialUpdate(String id, ContractDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<ContractDTO> bulkCreate(List<ContractDTO> dtos);
    void bulkDelete(List<String> ids);
}