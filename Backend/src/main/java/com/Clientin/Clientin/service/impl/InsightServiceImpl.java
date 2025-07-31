package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Insight;
import com.Clientin.Clientin.dto.InsightDTO;
import com.Clientin.Clientin.mapper.InsightMapper;
import com.Clientin.Clientin.repository.InsightRepository;
import com.Clientin.Clientin.service.InsightService;
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
public class InsightServiceImpl implements InsightService {

    private final InsightRepository insightRepository;
    private final InsightMapper insightMapper;

    @Override
    @Transactional
    public InsightDTO create(InsightDTO dto) {
        log.debug("Creating new Insight");
        try {
            Insight entity = insightMapper.toEntity(dto);
            return insightMapper.toDTO(insightRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InsightDTO> findById(String id) {
        log.debug("Fetching Insight with ID: {}", id);
        return insightRepository.findById(id)
                .map(insightMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsightDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Insight results");
        return insightRepository.findAll(pageable)
                .map(insightMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsightDTO> findAll() {
        log.debug("Fetching all Insight entities");
        return insightMapper.toDTOList(insightRepository.findAll());
    }

    @Override
    @Transactional
    public InsightDTO update(String id, InsightDTO dto) {
        log.debug("Updating Insight with ID: {}", id);
        return insightRepository.findById(id)
                .map(existingEntity -> {
                    insightMapper.partialUpdate(dto, existingEntity);
                    return insightMapper.toDTO(insightRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Insight not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Insight with ID: {}", id);
        if (!insightRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Insight not found with id: " + id
            );
        }
        insightRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsightDTO> search(Specification<Insight> spec, Pageable pageable) {
        log.debug("Searching Insight with specification");
        return insightRepository.findAll(spec, pageable)
                .map(insightMapper::toDTO);
    }

    @Override
    @Transactional
    public InsightDTO partialUpdate(String id, InsightDTO dto) {
        log.debug("Partial update for Insight ID: {}", id);
        return insightRepository.findById(id)
                .map(entity -> {
                    insightMapper.partialUpdate(dto, entity);
                    return insightMapper.toDTO(insightRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Insight not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return insightRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<InsightDTO> bulkCreate(List<InsightDTO> dtos) {
        log.debug("Bulk creating Insight entities: {} items", dtos.size());
        List<Insight> entities = insightMapper.toEntityList(dtos);
        return insightMapper.toDTOList(insightRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Insight entities: {} items", ids.size());
        insightRepository.deleteAllById(ids);
    }
}