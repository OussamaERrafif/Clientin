package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.ClientDTO;
import com.Clientin.Clientin.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    
    // Basic CRUD operations
    ClientDTO create(ClientDTO dto);
    Optional<ClientDTO> findById(String id);
    Page<ClientDTO> findAll(Pageable pageable);
    List<ClientDTO> findAll();
    ClientDTO update(String id, ClientDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<ClientDTO> search(Specification<Client> spec, Pageable pageable);
    ClientDTO partialUpdate(String id, ClientDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<ClientDTO> bulkCreate(List<ClientDTO> dtos);
    void bulkDelete(List<String> ids);
}