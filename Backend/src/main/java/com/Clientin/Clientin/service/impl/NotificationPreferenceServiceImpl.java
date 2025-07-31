package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.NotificationPreference;
import com.Clientin.Clientin.dto.NotificationPreferenceDTO;
import com.Clientin.Clientin.mapper.NotificationPreferenceMapper;
import com.Clientin.Clientin.repository.NotificationPreferenceRepository;
import com.Clientin.Clientin.service.NotificationPreferenceService;
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
public class NotificationPreferenceServiceImpl implements NotificationPreferenceService {

    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final NotificationPreferenceMapper notificationPreferenceMapper;

    @Override
    @Transactional
    public NotificationPreferenceDTO create(NotificationPreferenceDTO dto) {
        log.debug("Creating new NotificationPreference");
        try {
            NotificationPreference entity = notificationPreferenceMapper.toEntity(dto);
            return notificationPreferenceMapper.toDTO(notificationPreferenceRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationPreferenceDTO> findById(String id) {
        log.debug("Fetching NotificationPreference with ID: {}", id);
        return notificationPreferenceRepository.findById(id)
                .map(notificationPreferenceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationPreferenceDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged NotificationPreference results");
        return notificationPreferenceRepository.findAll(pageable)
                .map(notificationPreferenceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationPreferenceDTO> findAll() {
        log.debug("Fetching all NotificationPreference entities");
        return notificationPreferenceMapper.toDTOList(notificationPreferenceRepository.findAll());
    }

    @Override
    @Transactional
    public NotificationPreferenceDTO update(String id, NotificationPreferenceDTO dto) {
        log.debug("Updating NotificationPreference with ID: {}", id);
        return notificationPreferenceRepository.findById(id)
                .map(existingEntity -> {
                    notificationPreferenceMapper.partialUpdate(dto, existingEntity);
                    return notificationPreferenceMapper.toDTO(notificationPreferenceRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NotificationPreference not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting NotificationPreference with ID: {}", id);
        if (!notificationPreferenceRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "NotificationPreference not found with id: " + id
            );
        }
        notificationPreferenceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationPreferenceDTO> search(Specification<NotificationPreference> spec, Pageable pageable) {
        log.debug("Searching NotificationPreference with specification");
        return notificationPreferenceRepository.findAll(spec, pageable)
                .map(notificationPreferenceMapper::toDTO);
    }

    @Override
    @Transactional
    public NotificationPreferenceDTO partialUpdate(String id, NotificationPreferenceDTO dto) {
        log.debug("Partial update for NotificationPreference ID: {}", id);
        return notificationPreferenceRepository.findById(id)
                .map(entity -> {
                    notificationPreferenceMapper.partialUpdate(dto, entity);
                    return notificationPreferenceMapper.toDTO(notificationPreferenceRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NotificationPreference not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return notificationPreferenceRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<NotificationPreferenceDTO> bulkCreate(List<NotificationPreferenceDTO> dtos) {
        log.debug("Bulk creating NotificationPreference entities: {} items", dtos.size());
        List<NotificationPreference> entities = notificationPreferenceMapper.toEntityList(dtos);
        return notificationPreferenceMapper.toDTOList(notificationPreferenceRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting NotificationPreference entities: {} items", ids.size());
        notificationPreferenceRepository.deleteAllById(ids);
    }
}