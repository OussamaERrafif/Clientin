package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Reward;
import com.Clientin.Clientin.dto.RewardDTO;
import com.Clientin.Clientin.mapper.RewardMapper;
import com.Clientin.Clientin.repository.RewardRepository;
import com.Clientin.Clientin.service.RewardService;
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
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;

    @Override
    @Transactional
    public RewardDTO create(RewardDTO dto) {
        log.debug("Creating new Reward");
        try {
            Reward entity = rewardMapper.toEntity(dto);
            return rewardMapper.toDTO(rewardRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RewardDTO> findById(String id) {
        log.debug("Fetching Reward with ID: {}", id);
        return rewardRepository.findById(id)
                .map(rewardMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Reward results");
        return rewardRepository.findAll(pageable)
                .map(rewardMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardDTO> findAll() {
        log.debug("Fetching all Reward entities");
        return rewardMapper.toDTOList(rewardRepository.findAll());
    }

    @Override
    @Transactional
    public RewardDTO update(String id, RewardDTO dto) {
        log.debug("Updating Reward with ID: {}", id);
        return rewardRepository.findById(id)
                .map(existingEntity -> {
                    rewardMapper.partialUpdate(dto, existingEntity);
                    return rewardMapper.toDTO(rewardRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Reward not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Reward with ID: {}", id);
        if (!rewardRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Reward not found with id: " + id
            );
        }
        rewardRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardDTO> search(Specification<Reward> spec, Pageable pageable) {
        log.debug("Searching Reward with specification");
        return rewardRepository.findAll(spec, pageable)
                .map(rewardMapper::toDTO);
    }

    @Override
    @Transactional
    public RewardDTO partialUpdate(String id, RewardDTO dto) {
        log.debug("Partial update for Reward ID: {}", id);
        return rewardRepository.findById(id)
                .map(entity -> {
                    rewardMapper.partialUpdate(dto, entity);
                    return rewardMapper.toDTO(rewardRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Reward not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return rewardRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<RewardDTO> bulkCreate(List<RewardDTO> dtos) {
        log.debug("Bulk creating Reward entities: {} items", dtos.size());
        List<Reward> entities = rewardMapper.toEntityList(dtos);
        return rewardMapper.toDTOList(rewardRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Reward entities: {} items", ids.size());
        rewardRepository.deleteAllById(ids);
    }
}