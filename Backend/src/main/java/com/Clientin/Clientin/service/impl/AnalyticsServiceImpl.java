package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Analytics;
import com.Clientin.Clientin.dto.AnalyticsDTO;
import com.Clientin.Clientin.mapper.AnalyticsMapper;
import com.Clientin.Clientin.repository.AnalyticsRepository;
import com.Clientin.Clientin.service.AnalyticsService;
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
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final AnalyticsMapper analyticsMapper;

    @Override
    @Transactional
    public AnalyticsDTO create(AnalyticsDTO dto) {
        log.debug("Creating new Analytics");
        try {
            Analytics entity = analyticsMapper.toEntity(dto);
            return analyticsMapper.toDTO(analyticsRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyticsDTO> findById(String id) {
        log.debug("Fetching Analytics with ID: {}", id);
        return analyticsRepository.findById(id)
                .map(analyticsMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyticsDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Analytics results");
        return analyticsRepository.findAll(pageable)
                .map(analyticsMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalyticsDTO> findAll() {
        log.debug("Fetching all Analytics entities");
        return analyticsMapper.toDTOList(analyticsRepository.findAll());
    }

    @Override
    @Transactional
    public AnalyticsDTO update(String id, AnalyticsDTO dto) {
        log.debug("Updating Analytics with ID: {}", id);
        return analyticsRepository.findById(id)
                .map(existingEntity -> {
                    analyticsMapper.partialUpdate(dto, existingEntity);
                    return analyticsMapper.toDTO(analyticsRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Analytics not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Analytics with ID: {}", id);
        if (!analyticsRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Analytics not found with id: " + id
            );
        }
        analyticsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyticsDTO> search(Specification<Analytics> spec, Pageable pageable) {
        log.debug("Searching Analytics with specification");
        return analyticsRepository.findAll(spec, pageable)
                .map(analyticsMapper::toDTO);
    }

    @Override
    @Transactional
    public AnalyticsDTO partialUpdate(String id, AnalyticsDTO dto) {
        log.debug("Partial update for Analytics ID: {}", id);
        return analyticsRepository.findById(id)
                .map(entity -> {
                    analyticsMapper.partialUpdate(dto, entity);
                    return analyticsMapper.toDTO(analyticsRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Analytics not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return analyticsRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<AnalyticsDTO> bulkCreate(List<AnalyticsDTO> dtos) {
        log.debug("Bulk creating Analytics entities: {} items", dtos.size());
        List<Analytics> entities = analyticsMapper.toEntityList(dtos);
        return analyticsMapper.toDTOList(analyticsRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Analytics entities: {} items", ids.size());
        analyticsRepository.deleteAllById(ids);
    }
}