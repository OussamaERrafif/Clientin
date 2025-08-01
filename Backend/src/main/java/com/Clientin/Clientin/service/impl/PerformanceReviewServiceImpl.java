package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.PerformanceReview;
import com.Clientin.Clientin.dto.PerformanceReviewDTO;
import com.Clientin.Clientin.mapper.PerformanceReviewMapper;
import com.Clientin.Clientin.repository.PerformanceReviewRepository;
import com.Clientin.Clientin.service.PerformanceReviewService;
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
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final PerformanceReviewMapper performanceReviewMapper;

    @Override
    @Transactional
    public PerformanceReviewDTO create(PerformanceReviewDTO dto) {
        log.debug("Creating new PerformanceReview");
        try {
            PerformanceReview entity = performanceReviewMapper.toEntity(dto);
            return performanceReviewMapper.toDTO(performanceReviewRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PerformanceReviewDTO> findById(String id) {
        log.debug("Fetching PerformanceReview with ID: {}", id);
        return performanceReviewRepository.findById(id)
                .map(performanceReviewMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged PerformanceReview results");
        return performanceReviewRepository.findAll(pageable)
                .map(performanceReviewMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceReviewDTO> findAll() {
        log.debug("Fetching all PerformanceReview entities");
        return performanceReviewMapper.toDTOList(performanceReviewRepository.findAll());
    }

    @Override
    @Transactional
    public PerformanceReviewDTO update(String id, PerformanceReviewDTO dto) {
        log.debug("Updating PerformanceReview with ID: {}", id);
        return performanceReviewRepository.findById(id)
                .map(existingEntity -> {
                    performanceReviewMapper.partialUpdate(dto, existingEntity);
                    return performanceReviewMapper.toDTO(performanceReviewRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "PerformanceReview not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting PerformanceReview with ID: {}", id);
        if (!performanceReviewRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "PerformanceReview not found with id: " + id
            );
        }
        performanceReviewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewDTO> search(Specification<PerformanceReview> spec, Pageable pageable) {
        log.debug("Searching PerformanceReview with specification");
        return performanceReviewRepository.findAll(spec, pageable)
                .map(performanceReviewMapper::toDTO);
    }

    @Override
    @Transactional
    public PerformanceReviewDTO partialUpdate(String id, PerformanceReviewDTO dto) {
        log.debug("Partial update for PerformanceReview ID: {}", id);
        return performanceReviewRepository.findById(id)
                .map(entity -> {
                    performanceReviewMapper.partialUpdate(dto, entity);
                    return performanceReviewMapper.toDTO(performanceReviewRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "PerformanceReview not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return performanceReviewRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<PerformanceReviewDTO> bulkCreate(List<PerformanceReviewDTO> dtos) {
        log.debug("Bulk creating PerformanceReview entities: {} items", dtos.size());
        List<PerformanceReview> entities = performanceReviewMapper.toEntityList(dtos);
        return performanceReviewMapper.toDTOList(performanceReviewRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting PerformanceReview entities: {} items", ids.size());
        performanceReviewRepository.deleteAllById(ids);
    }
}