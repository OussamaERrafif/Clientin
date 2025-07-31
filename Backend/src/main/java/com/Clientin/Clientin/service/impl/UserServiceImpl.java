package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.dto.UserDTO;
import com.Clientin.Clientin.mapper.UserMapper;
import com.Clientin.Clientin.repository.UserRepository;
import com.Clientin.Clientin.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO create(UserDTO dto) {
        log.debug("Creating new User");
        try {
            User entity = userMapper.toEntity(dto);
            return userMapper.toDTO(userRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(String id) {
        log.debug("Fetching User with ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged User results");
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Fetching all User entities");
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserDTO update(String id, UserDTO dto) {
        log.debug("Updating User with ID: {}", id);
        return userRepository.findById(id)
                .map(existingEntity -> {
                    userMapper.partialUpdate(dto, existingEntity);
                    return userMapper.toDTO(userRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "User not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting User with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "User not found with id: " + id
            );
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> search(Specification<User> spec, Pageable pageable) {
        log.debug("Searching User with specification");
        return userRepository.findAll(spec, pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO partialUpdate(String id, UserDTO dto) {
        log.debug("Partial update for User ID: {}", id);
        return userRepository.findById(id)
                .map(entity -> {
                    userMapper.partialUpdate(dto, entity);
                    return userMapper.toDTO(userRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "User not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<UserDTO> bulkCreate(List<UserDTO> dtos) {
        log.debug("Bulk creating User entities: {} items", dtos.size());
        List<User> entities = userMapper.toEntityList(dtos);
        return userMapper.toDTOList(userRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting User entities: {} items", ids.size());
        userRepository.deleteAllById(ids);
    }
}