package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Subscription;
import com.Clientin.Clientin.dto.SubscriptionDTO;
import com.Clientin.Clientin.mapper.SubscriptionMapper;
import com.Clientin.Clientin.repository.SubscriptionRepository;
import com.Clientin.Clientin.service.SubscriptionService;
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
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionDTO create(SubscriptionDTO dto) {
        log.debug("Creating new Subscription");
        try {
            Subscription entity = subscriptionMapper.toEntity(dto);
            return subscriptionMapper.toDTO(subscriptionRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubscriptionDTO> findById(String id) {
        log.debug("Fetching Subscription with ID: {}", id);
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubscriptionDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Subscription results");
        return subscriptionRepository.findAll(pageable)
                .map(subscriptionMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDTO> findAll() {
        log.debug("Fetching all Subscription entities");
        return subscriptionMapper.toDTOList(subscriptionRepository.findAll());
    }

    @Override
    @Transactional
    public SubscriptionDTO update(String id, SubscriptionDTO dto) {
        log.debug("Updating Subscription with ID: {}", id);
        return subscriptionRepository.findById(id)
                .map(existingEntity -> {
                    subscriptionMapper.partialUpdate(dto, existingEntity);
                    return subscriptionMapper.toDTO(subscriptionRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Subscription not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Subscription with ID: {}", id);
        if (!subscriptionRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Subscription not found with id: " + id
            );
        }
        subscriptionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubscriptionDTO> search(Specification<Subscription> spec, Pageable pageable) {
        log.debug("Searching Subscription with specification");
        return subscriptionRepository.findAll(spec, pageable)
                .map(subscriptionMapper::toDTO);
    }

    @Override
    @Transactional
    public SubscriptionDTO partialUpdate(String id, SubscriptionDTO dto) {
        log.debug("Partial update for Subscription ID: {}", id);
        return subscriptionRepository.findById(id)
                .map(entity -> {
                    subscriptionMapper.partialUpdate(dto, entity);
                    return subscriptionMapper.toDTO(subscriptionRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Subscription not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return subscriptionRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<SubscriptionDTO> bulkCreate(List<SubscriptionDTO> dtos) {
        log.debug("Bulk creating Subscription entities: {} items", dtos.size());
        List<Subscription> entities = subscriptionMapper.toEntityList(dtos);
        return subscriptionMapper.toDTOList(subscriptionRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Subscription entities: {} items", ids.size());
        subscriptionRepository.deleteAllById(ids);
    }
}