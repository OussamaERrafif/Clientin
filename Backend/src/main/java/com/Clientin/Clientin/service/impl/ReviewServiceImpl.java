package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Review;
import com.Clientin.Clientin.dto.ReviewDTO;
import com.Clientin.Clientin.mapper.ReviewMapper;
import com.Clientin.Clientin.repository.ReviewRepository;
import com.Clientin.Clientin.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO dto) {
        log.debug("Creating new Review");
        try {
            Review entity = reviewMapper.toEntity(dto);
            return reviewMapper.toDTO(reviewRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findById(String id) {
        log.debug("Fetching Review with ID: {}", id);
        return reviewRepository.findById(id)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Review results");
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        log.debug("Fetching all Review entities");
        return reviewMapper.toDTOList(reviewRepository.findAll());
    }

    @Override
    @Transactional
    public ReviewDTO update(String id, ReviewDTO dto) {
        log.debug("Updating Review with ID: {}", id);
        return reviewRepository.findById(id)
                .map(existingEntity -> {
                    reviewMapper.partialUpdate(dto, existingEntity);
                    return reviewMapper.toDTO(reviewRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Review not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Review with ID: {}", id);
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Review not found with id: " + id
            );
        }
        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> search(Specification<Review> spec, Pageable pageable) {
        log.debug("Searching Review with specification");
        return reviewRepository.findAll(spec, pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional
    public ReviewDTO partialUpdate(String id, ReviewDTO dto) {
        log.debug("Partial update for Review ID: {}", id);
        return reviewRepository.findById(id)
                .map(entity -> {
                    reviewMapper.partialUpdate(dto, entity);
                    return reviewMapper.toDTO(reviewRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Review not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return reviewRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<ReviewDTO> bulkCreate(List<ReviewDTO> dtos) {
        log.debug("Bulk creating Review entities: {} items", dtos.size());
        List<Review> entities = reviewMapper.toEntityList(dtos);
        return reviewMapper.toDTOList(reviewRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Review entities: {} items", ids.size());
        reviewRepository.deleteAllById(ids);
    }
}