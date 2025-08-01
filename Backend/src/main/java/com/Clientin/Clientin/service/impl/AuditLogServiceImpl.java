package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.AuditLog;
import com.Clientin.Clientin.dto.AuditLogDTO;
import com.Clientin.Clientin.mapper.AuditLogMapper;
import com.Clientin.Clientin.repository.AuditLogRepository;
import com.Clientin.Clientin.service.AuditLogService;
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
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional
    public AuditLogDTO create(AuditLogDTO dto) {
        log.debug("Creating new AuditLog");
        try {
            AuditLog entity = auditLogMapper.toEntity(dto);
            return auditLogMapper.toDTO(auditLogRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuditLogDTO> findById(String id) {
        log.debug("Fetching AuditLog with ID: {}", id);
        return auditLogRepository.findById(id)
                .map(auditLogMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged AuditLog results");
        return auditLogRepository.findAll(pageable)
                .map(auditLogMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogDTO> findAll() {
        log.debug("Fetching all AuditLog entities");
        return auditLogMapper.toDTOList(auditLogRepository.findAll());
    }

    @Override
    @Transactional
    public AuditLogDTO update(String id, AuditLogDTO dto) {
        log.debug("Updating AuditLog with ID: {}", id);
        return auditLogRepository.findById(id)
                .map(existingEntity -> {
                    auditLogMapper.partialUpdate(dto, existingEntity);
                    return auditLogMapper.toDTO(auditLogRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AuditLog not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting AuditLog with ID: {}", id);
        if (!auditLogRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "AuditLog not found with id: " + id
            );
        }
        auditLogRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDTO> search(Specification<AuditLog> spec, Pageable pageable) {
        log.debug("Searching AuditLog with specification");
        return auditLogRepository.findAll(spec, pageable)
                .map(auditLogMapper::toDTO);
    }

    @Override
    @Transactional
    public AuditLogDTO partialUpdate(String id, AuditLogDTO dto) {
        log.debug("Partial update for AuditLog ID: {}", id);
        return auditLogRepository.findById(id)
                .map(entity -> {
                    auditLogMapper.partialUpdate(dto, entity);
                    return auditLogMapper.toDTO(auditLogRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AuditLog not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return auditLogRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<AuditLogDTO> bulkCreate(List<AuditLogDTO> dtos) {
        log.debug("Bulk creating AuditLog entities: {} items", dtos.size());
        List<AuditLog> entities = auditLogMapper.toEntityList(dtos);
        return auditLogMapper.toDTOList(auditLogRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting AuditLog entities: {} items", ids.size());
        auditLogRepository.deleteAllById(ids);
    }
}