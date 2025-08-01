package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.NFCSession;
import com.Clientin.Clientin.dto.NFCSessionDTO;
import com.Clientin.Clientin.mapper.NFCSessionMapper;
import com.Clientin.Clientin.repository.NFCSessionRepository;
import com.Clientin.Clientin.service.NFCSessionService;
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
public class NFCSessionServiceImpl implements NFCSessionService {

    private final NFCSessionRepository nFCSessionRepository;
    private final NFCSessionMapper nFCSessionMapper;

    @Override
    @Transactional
    public NFCSessionDTO create(NFCSessionDTO dto) {
        log.debug("Creating new NFCSession");
        try {
            NFCSession entity = nFCSessionMapper.toEntity(dto);
            return nFCSessionMapper.toDTO(nFCSessionRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NFCSessionDTO> findById(String id) {
        log.debug("Fetching NFCSession with ID: {}", id);
        return nFCSessionRepository.findById(id)
                .map(nFCSessionMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NFCSessionDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged NFCSession results");
        return nFCSessionRepository.findAll(pageable)
                .map(nFCSessionMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NFCSessionDTO> findAll() {
        log.debug("Fetching all NFCSession entities");
        return nFCSessionMapper.toDTOList(nFCSessionRepository.findAll());
    }

    @Override
    @Transactional
    public NFCSessionDTO update(String id, NFCSessionDTO dto) {
        log.debug("Updating NFCSession with ID: {}", id);
        return nFCSessionRepository.findById(id)
                .map(existingEntity -> {
                    nFCSessionMapper.partialUpdate(dto, existingEntity);
                    return nFCSessionMapper.toDTO(nFCSessionRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NFCSession not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting NFCSession with ID: {}", id);
        if (!nFCSessionRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "NFCSession not found with id: " + id
            );
        }
        nFCSessionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NFCSessionDTO> search(Specification<NFCSession> spec, Pageable pageable) {
        log.debug("Searching NFCSession with specification");
        return nFCSessionRepository.findAll(spec, pageable)
                .map(nFCSessionMapper::toDTO);
    }

    @Override
    @Transactional
    public NFCSessionDTO partialUpdate(String id, NFCSessionDTO dto) {
        log.debug("Partial update for NFCSession ID: {}", id);
        return nFCSessionRepository.findById(id)
                .map(entity -> {
                    nFCSessionMapper.partialUpdate(dto, entity);
                    return nFCSessionMapper.toDTO(nFCSessionRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NFCSession not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return nFCSessionRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<NFCSessionDTO> bulkCreate(List<NFCSessionDTO> dtos) {
        log.debug("Bulk creating NFCSession entities: {} items", dtos.size());
        List<NFCSession> entities = nFCSessionMapper.toEntityList(dtos);
        return nFCSessionMapper.toDTOList(nFCSessionRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting NFCSession entities: {} items", ids.size());
        nFCSessionRepository.deleteAllById(ids);
    }
}