package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Feedback;
import com.Clientin.Clientin.dto.FeedbackDTO;
import com.Clientin.Clientin.mapper.FeedbackMapper;
import com.Clientin.Clientin.repository.FeedbackRepository;
import com.Clientin.Clientin.service.FeedbackService;
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
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public FeedbackDTO create(FeedbackDTO dto) {
        log.debug("Creating new Feedback");
        try {
            Feedback entity = feedbackMapper.toEntity(dto);
            return feedbackMapper.toDTO(feedbackRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackDTO> findById(String id) {
        log.debug("Fetching Feedback with ID: {}", id);
        return feedbackRepository.findById(id)
                .map(feedbackMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Feedback results");
        return feedbackRepository.findAll(pageable)
                .map(feedbackMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> findAll() {
        log.debug("Fetching all Feedback entities");
        return feedbackMapper.toDTOList(feedbackRepository.findAll());
    }

    @Override
    @Transactional
    public FeedbackDTO update(String id, FeedbackDTO dto) {
        log.debug("Updating Feedback with ID: {}", id);
        return feedbackRepository.findById(id)
                .map(existingEntity -> {
                    feedbackMapper.partialUpdate(dto, existingEntity);
                    return feedbackMapper.toDTO(feedbackRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Feedback not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Feedback with ID: {}", id);
        if (!feedbackRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Feedback not found with id: " + id
            );
        }
        feedbackRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> search(Specification<Feedback> spec, Pageable pageable) {
        log.debug("Searching Feedback with specification");
        return feedbackRepository.findAll(spec, pageable)
                .map(feedbackMapper::toDTO);
    }

    @Override
    @Transactional
    public FeedbackDTO partialUpdate(String id, FeedbackDTO dto) {
        log.debug("Partial update for Feedback ID: {}", id);
        return feedbackRepository.findById(id)
                .map(entity -> {
                    feedbackMapper.partialUpdate(dto, entity);
                    return feedbackMapper.toDTO(feedbackRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Feedback not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return feedbackRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<FeedbackDTO> bulkCreate(List<FeedbackDTO> dtos) {
        log.debug("Bulk creating Feedback entities: {} items", dtos.size());
        List<Feedback> entities = feedbackMapper.toEntityList(dtos);
        return feedbackMapper.toDTOList(feedbackRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Feedback entities: {} items", ids.size());
        feedbackRepository.deleteAllById(ids);
    }
}