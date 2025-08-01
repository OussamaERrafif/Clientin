package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Notification;
import com.Clientin.Clientin.dto.NotificationDTO;
import com.Clientin.Clientin.mapper.NotificationMapper;
import com.Clientin.Clientin.repository.NotificationRepository;
import com.Clientin.Clientin.service.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public NotificationDTO create(NotificationDTO dto) {
        log.debug("Creating new Notification");
        try {
            Notification entity = notificationMapper.toEntity(dto);
            return notificationMapper.toDTO(notificationRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDTO> findById(String id) {
        log.debug("Fetching Notification with ID: {}", id);
        return notificationRepository.findById(id)
                .map(notificationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Notification results");
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> findAll() {
        log.debug("Fetching all Notification entities");
        return notificationMapper.toDTOList(notificationRepository.findAll());
    }

    @Override
    @Transactional
    public NotificationDTO update(String id, NotificationDTO dto) {
        log.debug("Updating Notification with ID: {}", id);
        return notificationRepository.findById(id)
                .map(existingEntity -> {
                    notificationMapper.partialUpdate(dto, existingEntity);
                    return notificationMapper.toDTO(notificationRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Notification not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Notification not found with id: " + id
            );
        }
        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDTO> search(Specification<Notification> spec, Pageable pageable) {
        log.debug("Searching Notification with specification");
        return notificationRepository.findAll(spec, pageable)
                .map(notificationMapper::toDTO);
    }

    @Override
    @Transactional
    public NotificationDTO partialUpdate(String id, NotificationDTO dto) {
        log.debug("Partial update for Notification ID: {}", id);
        return notificationRepository.findById(id)
                .map(entity -> {
                    notificationMapper.partialUpdate(dto, entity);
                    return notificationMapper.toDTO(notificationRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Notification not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return notificationRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<NotificationDTO> bulkCreate(List<NotificationDTO> dtos) {
        log.debug("Bulk creating Notification entities: {} items", dtos.size());
        List<Notification> entities = notificationMapper.toEntityList(dtos);
        return notificationMapper.toDTOList(notificationRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Notification entities: {} items", ids.size());
        notificationRepository.deleteAllById(ids);
    }
}