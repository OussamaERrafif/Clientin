package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Feedback;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.dto.FeedbackDTO;
import com.Clientin.Clientin.mapper.FeedbackMapper;
import com.Clientin.Clientin.repository.FeedbackRepository;
import com.Clientin.Clientin.service.FeedbackService;
import com.Clientin.Clientin.service.LogService;
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
    private final LogService logService;

    @Override
    @Transactional
    public FeedbackDTO create(FeedbackDTO dto) {
        log.debug("Creating new Feedback");
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Feedback", null, "system", "Starting feedback creation transaction");
            Feedback entity = feedbackMapper.toEntity(dto);
            Feedback saved = feedbackRepository.save(entity);
            logService.logCreate("Feedback", saved.getId(), "system", "Feedback entity created and saved to database");
            return feedbackMapper.toDTO(saved);
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to create feedback", e.getMessage());
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackDTO> findById(String id) {
        log.debug("Fetching Feedback with ID: {}", id);
        try {
            logService.logRead("Feedback", id, "system", "Starting feedback retrieval transaction");
            Optional<Feedback> feedback = feedbackRepository.findById(id);
            if (feedback.isPresent()) {
                logService.logRead("Feedback", id, "system", "Feedback found and retrieved successfully");
            } else {
                logService.logAction(Log.LogLevel.WARN, Log.LogAction.READ, "Feedback", id, "system", "Feedback not found");
            }
            return feedback.map(feedbackMapper::toDTO);
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to retrieve feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Feedback results");
        try {
            logService.logRead("Feedback", null, "system", "Starting paginated feedback retrieval transaction");
            Page<FeedbackDTO> result = feedbackRepository.findAll(pageable)
                    .map(feedbackMapper::toDTO);
            logService.logRead("Feedback", null, "system", "Retrieved " + result.getTotalElements() + " feedback records");
            return result;
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to retrieve paginated feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> findAll() {
        log.debug("Fetching all Feedback entities");
        try {
            logService.logRead("Feedback", null, "system", "Starting all feedback retrieval transaction");
            List<FeedbackDTO> result = feedbackMapper.toDTOList(feedbackRepository.findAll());
            logService.logRead("Feedback", null, "system", "Retrieved " + result.size() + " feedback records");
            return result;
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to retrieve all feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public FeedbackDTO update(String id, FeedbackDTO dto) {
        log.debug("Updating Feedback with ID: {}", id);
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Feedback", id, "system", "Starting feedback update transaction");
            return feedbackRepository.findById(id)
                    .map(existingEntity -> {
                        feedbackMapper.partialUpdate(dto, existingEntity);
                        Feedback updated = feedbackRepository.save(existingEntity);
                        logService.logUpdate("Feedback", id, "system", "Feedback entity updated in database");
                        return feedbackMapper.toDTO(updated);
                    })
                    .orElseThrow(() -> {
                        logService.logError("Feedback", id, "system", "Feedback not found for update", "Entity not found");
                        return new EntityNotFoundException("Feedback not found with id: " + id);
                    });
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to update feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Feedback with ID: {}", id);
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Feedback", id, "system", "Starting feedback deletion transaction");
            if (!feedbackRepository.existsById(id)) {
                logService.logError("Feedback", id, "system", "Feedback not found for deletion", "Entity not found");
                throw new EntityNotFoundException("Feedback not found with id: " + id);
            }
            feedbackRepository.deleteById(id);
            logService.logDelete("Feedback", id, "system", "Feedback entity deleted from database");
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to delete feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> search(Specification<Feedback> spec, Pageable pageable) {
        log.debug("Searching Feedback with specification");
        try {
            logService.logRead("Feedback", null, "system", "Starting feedback search transaction");
            Page<FeedbackDTO> result = feedbackRepository.findAll(spec, pageable)
                    .map(feedbackMapper::toDTO);
            logService.logRead("Feedback", null, "system", "Search completed, found " + result.getTotalElements() + " feedback records");
            return result;
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to search feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public FeedbackDTO partialUpdate(String id, FeedbackDTO dto) {
        log.debug("Partial update for Feedback ID: {}", id);
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Feedback", id, "system", "Starting feedback partial update transaction");
            return feedbackRepository.findById(id)
                    .map(entity -> {
                        feedbackMapper.partialUpdate(dto, entity);
                        Feedback updated = feedbackRepository.save(entity);
                        logService.logUpdate("Feedback", id, "system", "Feedback entity partially updated in database");
                        return feedbackMapper.toDTO(updated);
                    })
                    .orElseThrow(() -> {
                        logService.logError("Feedback", id, "system", "Feedback not found for partial update", "Entity not found");
                        return new EntityNotFoundException("Feedback not found with id: " + id);
                    });
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to partially update feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        try {
            logService.logRead("Feedback", id, "system", "Checking feedback existence");
            boolean exists = feedbackRepository.existsById(id);
            logService.logRead("Feedback", id, "system", "Feedback existence check: " + exists);
            return exists;
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to check feedback existence", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<FeedbackDTO> bulkCreate(List<FeedbackDTO> dtos) {
        log.debug("Bulk creating Feedback entities: {} items", dtos.size());
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Feedback", null, "system", 
                               "Starting bulk feedback creation transaction for " + dtos.size() + " items");
            List<Feedback> entities = feedbackMapper.toEntityList(dtos);
            List<Feedback> saved = feedbackRepository.saveAll(entities);
            logService.logCreate("Feedback", null, "system", 
                               "Bulk created " + saved.size() + " feedback entities");
            return feedbackMapper.toDTOList(saved);
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to bulk create feedback", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Feedback entities: {} items", ids.size());
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Feedback", null, "system", 
                               "Starting bulk feedback deletion transaction for " + ids.size() + " items");
            feedbackRepository.deleteAllById(ids);
            logService.logDelete("Feedback", null, "system", 
                               "Bulk deleted " + ids.size() + " feedback entities");
        } catch (Exception e) {
            logService.logError("Feedback", null, "system", "Failed to bulk delete feedback", e.getMessage());
            throw e;
        }
    }
}