package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.PasswordResetToken;
import com.Clientin.Clientin.dto.PasswordResetTokenDTO;
import com.Clientin.Clientin.mapper.PasswordResetTokenMapper;
import com.Clientin.Clientin.repository.PasswordResetTokenRepository;
import com.Clientin.Clientin.service.PasswordResetTokenService;
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
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordResetTokenMapper passwordResetTokenMapper;

    @Override
    @Transactional
    public PasswordResetTokenDTO create(PasswordResetTokenDTO dto) {
        log.debug("Creating new PasswordResetToken");
        try {
            PasswordResetToken entity = passwordResetTokenMapper.toEntity(dto);
            return passwordResetTokenMapper.toDTO(passwordResetTokenRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PasswordResetTokenDTO> findById(String id) {
        log.debug("Fetching PasswordResetToken with ID: {}", id);
        return passwordResetTokenRepository.findById(id)
                .map(passwordResetTokenMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PasswordResetTokenDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged PasswordResetToken results");
        return passwordResetTokenRepository.findAll(pageable)
                .map(passwordResetTokenMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordResetTokenDTO> findAll() {
        log.debug("Fetching all PasswordResetToken entities");
        return passwordResetTokenMapper.toDTOList(passwordResetTokenRepository.findAll());
    }

    @Override
    @Transactional
    public PasswordResetTokenDTO update(String id, PasswordResetTokenDTO dto) {
        log.debug("Updating PasswordResetToken with ID: {}", id);
        return passwordResetTokenRepository.findById(id)
                .map(existingEntity -> {
                    passwordResetTokenMapper.partialUpdate(dto, existingEntity);
                    return passwordResetTokenMapper.toDTO(passwordResetTokenRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "PasswordResetToken not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting PasswordResetToken with ID: {}", id);
        if (!passwordResetTokenRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "PasswordResetToken not found with id: " + id
            );
        }
        passwordResetTokenRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PasswordResetTokenDTO> search(Specification<PasswordResetToken> spec, Pageable pageable) {
        log.debug("Searching PasswordResetToken with specification");
        return passwordResetTokenRepository.findAll(spec, pageable)
                .map(passwordResetTokenMapper::toDTO);
    }

    @Override
    @Transactional
    public PasswordResetTokenDTO partialUpdate(String id, PasswordResetTokenDTO dto) {
        log.debug("Partial update for PasswordResetToken ID: {}", id);
        return passwordResetTokenRepository.findById(id)
                .map(entity -> {
                    passwordResetTokenMapper.partialUpdate(dto, entity);
                    return passwordResetTokenMapper.toDTO(passwordResetTokenRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "PasswordResetToken not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return passwordResetTokenRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<PasswordResetTokenDTO> bulkCreate(List<PasswordResetTokenDTO> dtos) {
        log.debug("Bulk creating PasswordResetToken entities: {} items", dtos.size());
        List<PasswordResetToken> entities = passwordResetTokenMapper.toEntityList(dtos);
        return passwordResetTokenMapper.toDTOList(passwordResetTokenRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting PasswordResetToken entities: {} items", ids.size());
        passwordResetTokenRepository.deleteAllById(ids);
    }
}