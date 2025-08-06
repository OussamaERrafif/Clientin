package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Badge;
import com.Clientin.Clientin.dto.BadgeDTO;
import com.Clientin.Clientin.mapper.BadgeMapper;
import com.Clientin.Clientin.repository.BadgeRepository;
import com.Clientin.Clientin.service.BadgeService;
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
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;

    @Override
    @Transactional
    public BadgeDTO create(BadgeDTO dto) {
        log.debug("Creating new Badge");
        try {
            Badge entity = badgeMapper.toEntity(dto);
            return badgeMapper.toDTO(badgeRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeDTO> findById(String id) {
        log.debug("Fetching Badge with ID: {}", id);
        return badgeRepository.findById(id)
                .map(badgeMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Badge results");
        return badgeRepository.findAll(pageable)
                .map(badgeMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BadgeDTO> findAll() {
        log.debug("Fetching all Badge entities");
        return badgeMapper.toDTOList(badgeRepository.findAll());
    }

    @Override
    @Transactional
    public BadgeDTO update(String id, BadgeDTO dto) {
        log.debug("Updating Badge with ID: {}", id);
        return badgeRepository.findById(id)
                .map(existingEntity -> {
                    badgeMapper.partialUpdate(dto, existingEntity);
                    return badgeMapper.toDTO(badgeRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Badge not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Badge with ID: {}", id);
        if (!badgeRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Badge not found with id: " + id
            );
        }
        badgeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeDTO> search(Specification<Badge> spec, Pageable pageable) {
        log.debug("Searching Badge with specification");
        return badgeRepository.findAll(spec, pageable)
                .map(badgeMapper::toDTO);
    }

    @Override
    @Transactional
    public BadgeDTO partialUpdate(String id, BadgeDTO dto) {
        log.debug("Partial update for Badge ID: {}", id);
        return badgeRepository.findById(id)
                .map(entity -> {
                    badgeMapper.partialUpdate(dto, entity);
                    return badgeMapper.toDTO(badgeRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Badge not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return badgeRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<BadgeDTO> bulkCreate(List<BadgeDTO> dtos) {
        log.debug("Bulk creating Badge entities: {} items", dtos.size());
        List<Badge> entities = badgeMapper.toEntityList(dtos);
        return badgeMapper.toDTOList(badgeRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Badge entities: {} items", ids.size());
        badgeRepository.deleteAllById(ids);
    }
}