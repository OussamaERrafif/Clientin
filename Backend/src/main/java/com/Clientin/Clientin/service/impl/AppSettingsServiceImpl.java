package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.AppSettings;
import com.Clientin.Clientin.dto.AppSettingsDTO;
import com.Clientin.Clientin.mapper.AppSettingsMapper;
import com.Clientin.Clientin.repository.AppSettingsRepository;
import com.Clientin.Clientin.service.AppSettingsService;
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
public class AppSettingsServiceImpl implements AppSettingsService {

    private final AppSettingsRepository appSettingsRepository;
    private final AppSettingsMapper appSettingsMapper;

    @Override
    @Transactional
    public AppSettingsDTO create(AppSettingsDTO dto) {
        log.debug("Creating new AppSettings");
        try {
            AppSettings entity = appSettingsMapper.toEntity(dto);
            return appSettingsMapper.toDTO(appSettingsRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppSettingsDTO> findById(String id) {
        log.debug("Fetching AppSettings with ID: {}", id);
        return appSettingsRepository.findById(id)
                .map(appSettingsMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppSettingsDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged AppSettings results");
        return appSettingsRepository.findAll(pageable)
                .map(appSettingsMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppSettingsDTO> findAll() {
        log.debug("Fetching all AppSettings entities");
        return appSettingsMapper.toDTOList(appSettingsRepository.findAll());
    }

    @Override
    @Transactional
    public AppSettingsDTO update(String id, AppSettingsDTO dto) {
        log.debug("Updating AppSettings with ID: {}", id);
        return appSettingsRepository.findById(id)
                .map(existingEntity -> {
                    appSettingsMapper.partialUpdate(dto, existingEntity);
                    return appSettingsMapper.toDTO(appSettingsRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AppSettings not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting AppSettings with ID: {}", id);
        if (!appSettingsRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "AppSettings not found with id: " + id
            );
        }
        appSettingsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppSettingsDTO> search(Specification<AppSettings> spec, Pageable pageable) {
        log.debug("Searching AppSettings with specification");
        return appSettingsRepository.findAll(spec, pageable)
                .map(appSettingsMapper::toDTO);
    }

    @Override
    @Transactional
    public AppSettingsDTO partialUpdate(String id, AppSettingsDTO dto) {
        log.debug("Partial update for AppSettings ID: {}", id);
        return appSettingsRepository.findById(id)
                .map(entity -> {
                    appSettingsMapper.partialUpdate(dto, entity);
                    return appSettingsMapper.toDTO(appSettingsRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "AppSettings not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return appSettingsRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<AppSettingsDTO> bulkCreate(List<AppSettingsDTO> dtos) {
        log.debug("Bulk creating AppSettings entities: {} items", dtos.size());
        List<AppSettings> entities = appSettingsMapper.toEntityList(dtos);
        return appSettingsMapper.toDTOList(appSettingsRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting AppSettings entities: {} items", ids.size());
        appSettingsRepository.deleteAllById(ids);
    }
}