package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Training;
import com.Clientin.Clientin.dto.TrainingDTO;
import com.Clientin.Clientin.mapper.TrainingMapper;
import com.Clientin.Clientin.repository.TrainingRepository;
import com.Clientin.Clientin.service.TrainingService;
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
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    @Transactional
    public TrainingDTO create(TrainingDTO dto) {
        log.debug("Creating new Training");
        try {
            Training entity = trainingMapper.toEntity(dto);
            return trainingMapper.toDTO(trainingRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingDTO> findById(String id) {
        log.debug("Fetching Training with ID: {}", id);
        return trainingRepository.findById(id)
                .map(trainingMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Training results");
        return trainingRepository.findAll(pageable)
                .map(trainingMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingDTO> findAll() {
        log.debug("Fetching all Training entities");
        return trainingMapper.toDTOList(trainingRepository.findAll());
    }

    @Override
    @Transactional
    public TrainingDTO update(String id, TrainingDTO dto) {
        log.debug("Updating Training with ID: {}", id);
        return trainingRepository.findById(id)
                .map(existingEntity -> {
                    trainingMapper.partialUpdate(dto, existingEntity);
                    return trainingMapper.toDTO(trainingRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Training not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Training with ID: {}", id);
        if (!trainingRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Training not found with id: " + id
            );
        }
        trainingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingDTO> search(Specification<Training> spec, Pageable pageable) {
        log.debug("Searching Training with specification");
        return trainingRepository.findAll(spec, pageable)
                .map(trainingMapper::toDTO);
    }

    @Override
    @Transactional
    public TrainingDTO partialUpdate(String id, TrainingDTO dto) {
        log.debug("Partial update for Training ID: {}", id);
        return trainingRepository.findById(id)
                .map(entity -> {
                    trainingMapper.partialUpdate(dto, entity);
                    return trainingMapper.toDTO(trainingRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Training not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return trainingRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<TrainingDTO> bulkCreate(List<TrainingDTO> dtos) {
        log.debug("Bulk creating Training entities: {} items", dtos.size());
        List<Training> entities = trainingMapper.toEntityList(dtos);
        return trainingMapper.toDTOList(trainingRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Training entities: {} items", ids.size());
        trainingRepository.deleteAllById(ids);
    }
}