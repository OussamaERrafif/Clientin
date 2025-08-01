package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.AuthToken;
import com.Clientin.Clientin.dto.AuthTokenDTO;
import com.Clientin.Clientin.mapper.AuthTokenMapper;
import com.Clientin.Clientin.repository.AuthTokenRepository;
import com.Clientin.Clientin.service.AuthTokenService;
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
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final AuthTokenMapper authTokenMapper;

    @Override
    @Transactional
    public AuthTokenDTO create(AuthTokenDTO dto) {
        log.debug("Creating new AuthToken");
        try {
            AuthToken entity = authTokenMapper.toEntity(dto);
            return authTokenMapper.toDTO(authTokenRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthTokenDTO> findById(String id) {
        log.debug("Fetching AuthToken with ID: {}", id);
        return authTokenRepository.findById(id)
                .map(authTokenMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthTokenDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged AuthToken results");
        return authTokenRepository.findAll(pageable)
                .map(authTokenMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthTokenDTO> findAll() {
        log.debug("Fetching all AuthToken entities");
        return authTokenMapper.toDTOList(authTokenRepository.findAll());
    }

    @Override
    @Transactional
    public AuthTokenDTO update(String id, AuthTokenDTO dto) {
        log.debug("Updating AuthToken with ID: {}", id);
        return authTokenRepository.findById(id)
                .map(existingEntity -> {
                    authTokenMapper.partialUpdate(dto, existingEntity);
                    return authTokenMapper.toDTO(authTokenRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AuthToken not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting AuthToken with ID: {}", id);
        if (!authTokenRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "AuthToken not found with id: " + id
            );
        }
        authTokenRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthTokenDTO> search(Specification<AuthToken> spec, Pageable pageable) {
        log.debug("Searching AuthToken with specification");
        return authTokenRepository.findAll(spec, pageable)
                .map(authTokenMapper::toDTO);
    }

    @Override
    @Transactional
    public AuthTokenDTO partialUpdate(String id, AuthTokenDTO dto) {
        log.debug("Partial update for AuthToken ID: {}", id);
        return authTokenRepository.findById(id)
                .map(entity -> {
                    authTokenMapper.partialUpdate(dto, entity);
                    return authTokenMapper.toDTO(authTokenRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AuthToken not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return authTokenRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<AuthTokenDTO> bulkCreate(List<AuthTokenDTO> dtos) {
        log.debug("Bulk creating AuthToken entities: {} items", dtos.size());
        List<AuthToken> entities = authTokenMapper.toEntityList(dtos);
        return authTokenMapper.toDTOList(authTokenRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting AuthToken entities: {} items", ids.size());
        authTokenRepository.deleteAllById(ids);
    }
}