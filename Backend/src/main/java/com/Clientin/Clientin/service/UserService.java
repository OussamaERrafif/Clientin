package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.UserDTO;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    // Basic CRUD operations
    UserDTO create(UserDTO dto);
    Optional<UserDTO> findById(String id);
    Page<UserDTO> findAll(Pageable pageable);
    List<UserDTO> findAll();
    UserDTO update(String id, UserDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<UserDTO> search(Specification<User> spec, Pageable pageable);
    UserDTO partialUpdate(String id, UserDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<UserDTO> bulkCreate(List<UserDTO> dtos);
    void bulkDelete(List<String> ids);
}