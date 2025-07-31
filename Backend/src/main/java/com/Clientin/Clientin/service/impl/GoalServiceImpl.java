package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Goal;
import com.Clientin.Clientin.dto.GoalDTO;
import com.Clientin.Clientin.mapper.GoalMapper;
import com.Clientin.Clientin.repository.GoalRepository;
import com.Clientin.Clientin.service.GoalService;
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
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    @Override
    @Transactional
    public GoalDTO create(GoalDTO dto) {
        log.debug("Creating new Goal");
        try {
            Goal entity = goalMapper.toEntity(dto);
            return goalMapper.toDTO(goalRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoalDTO> findById(String id) {
        log.debug("Fetching Goal with ID: {}", id);
        return goalRepository.findById(id)
                .map(goalMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoalDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Goal results");
        return goalRepository.findAll(pageable)
                .map(goalMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDTO> findAll() {
        log.debug("Fetching all Goal entities");
        return goalMapper.toDTOList(goalRepository.findAll());
    }

    @Override
    @Transactional
    public GoalDTO update(String id, GoalDTO dto) {
        log.debug("Updating Goal with ID: {}", id);
        return goalRepository.findById(id)
                .map(existingEntity -> {
                    goalMapper.partialUpdate(dto, existingEntity);
                    return goalMapper.toDTO(goalRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Goal not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Goal with ID: {}", id);
        if (!goalRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Goal not found with id: " + id
            );
        }
        goalRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoalDTO> search(Specification<Goal> spec, Pageable pageable) {
        log.debug("Searching Goal with specification");
        return goalRepository.findAll(spec, pageable)
                .map(goalMapper::toDTO);
    }

    @Override
    @Transactional
    public GoalDTO partialUpdate(String id, GoalDTO dto) {
        log.debug("Partial update for Goal ID: {}", id);
        return goalRepository.findById(id)
                .map(entity -> {
                    goalMapper.partialUpdate(dto, entity);
                    return goalMapper.toDTO(goalRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Goal not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return goalRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<GoalDTO> bulkCreate(List<GoalDTO> dtos) {
        log.debug("Bulk creating Goal entities: {} items", dtos.size());
        List<Goal> entities = goalMapper.toEntityList(dtos);
        return goalMapper.toDTOList(goalRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Goal entities: {} items", ids.size());
        goalRepository.deleteAllById(ids);
    }
}