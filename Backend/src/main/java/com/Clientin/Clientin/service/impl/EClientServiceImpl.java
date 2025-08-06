package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.EClient;
import com.Clientin.Clientin.dto.EClientDTO;
import com.Clientin.Clientin.mapper.EClientMapper;
import com.Clientin.Clientin.repository.EClientRepository;
import com.Clientin.Clientin.service.EClientService;
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
public class EClientServiceImpl implements EClientService {

    private final EClientRepository eClientRepository;
    private final EClientMapper eClientMapper;

    @Override
    @Transactional
    public EClientDTO create(EClientDTO dto) {
        log.debug("Creating new EClient");
        try {
            EClient entity = eClientMapper.toEntity(dto);
            return eClientMapper.toDTO(eClientRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EClientDTO> findById(String id) {
        log.debug("Fetching EClient with ID: {}", id);
        return eClientRepository.findById(id)
                .map(eClientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EClientDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged EClient results");
        return eClientRepository.findAll(pageable)
                .map(eClientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EClientDTO> findAll() {
        log.debug("Fetching all EClient entities");
        return eClientMapper.toDTOList(eClientRepository.findAll());
    }

    @Override
    @Transactional
    public EClientDTO update(String id, EClientDTO dto) {
        log.debug("Updating EClient with ID: {}", id);
        return eClientRepository.findById(id)
                .map(existingEntity -> {
                    eClientMapper.partialUpdate(dto, existingEntity);
                    return eClientMapper.toDTO(eClientRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EClient not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting EClient with ID: {}", id);
        if (!eClientRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "EClient not found with id: " + id
            );
        }
        eClientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EClientDTO> search(Specification<EClient> spec, Pageable pageable) {
        log.debug("Searching EClient with specification");
        return eClientRepository.findAll(spec, pageable)
                .map(eClientMapper::toDTO);
    }

    @Override
    @Transactional
    public EClientDTO partialUpdate(String id, EClientDTO dto) {
        log.debug("Partial update for EClient ID: {}", id);
        return eClientRepository.findById(id)
                .map(entity -> {
                    eClientMapper.partialUpdate(dto, entity);
                    return eClientMapper.toDTO(eClientRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EClient not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return eClientRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<EClientDTO> bulkCreate(List<EClientDTO> dtos) {
        log.debug("Bulk creating EClient entities: {} items", dtos.size());
        List<EClient> entities = eClientMapper.toEntityList(dtos);
        return eClientMapper.toDTOList(eClientRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting EClient entities: {} items", ids.size());
        eClientRepository.deleteAllById(ids);
    }
}