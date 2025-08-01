package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.FileUpload;
import com.Clientin.Clientin.dto.FileUploadDTO;
import com.Clientin.Clientin.mapper.FileUploadMapper;
import com.Clientin.Clientin.repository.FileUploadRepository;
import com.Clientin.Clientin.service.FileUploadService;
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
public class FileUploadServiceImpl implements FileUploadService {

    private final FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;

    @Override
    @Transactional
    public FileUploadDTO create(FileUploadDTO dto) {
        log.debug("Creating new FileUpload");
        try {
            FileUpload entity = fileUploadMapper.toEntity(dto);
            return fileUploadMapper.toDTO(fileUploadRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileUploadDTO> findById(String id) {
        log.debug("Fetching FileUpload with ID: {}", id);
        return fileUploadRepository.findById(id)
                .map(fileUploadMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged FileUpload results");
        return fileUploadRepository.findAll(pageable)
                .map(fileUploadMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileUploadDTO> findAll() {
        log.debug("Fetching all FileUpload entities");
        return fileUploadMapper.toDTOList(fileUploadRepository.findAll());
    }

    @Override
    @Transactional
    public FileUploadDTO update(String id, FileUploadDTO dto) {
        log.debug("Updating FileUpload with ID: {}", id);
        return fileUploadRepository.findById(id)
                .map(existingEntity -> {
                    fileUploadMapper.partialUpdate(dto, existingEntity);
                    return fileUploadMapper.toDTO(fileUploadRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "FileUpload not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting FileUpload with ID: {}", id);
        if (!fileUploadRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "FileUpload not found with id: " + id
            );
        }
        fileUploadRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> search(Specification<FileUpload> spec, Pageable pageable) {
        log.debug("Searching FileUpload with specification");
        return fileUploadRepository.findAll(spec, pageable)
                .map(fileUploadMapper::toDTO);
    }

    @Override
    @Transactional
    public FileUploadDTO partialUpdate(String id, FileUploadDTO dto) {
        log.debug("Partial update for FileUpload ID: {}", id);
        return fileUploadRepository.findById(id)
                .map(entity -> {
                    fileUploadMapper.partialUpdate(dto, entity);
                    return fileUploadMapper.toDTO(fileUploadRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "FileUpload not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return fileUploadRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<FileUploadDTO> bulkCreate(List<FileUploadDTO> dtos) {
        log.debug("Bulk creating FileUpload entities: {} items", dtos.size());
        List<FileUpload> entities = fileUploadMapper.toEntityList(dtos);
        return fileUploadMapper.toDTOList(fileUploadRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting FileUpload entities: {} items", ids.size());
        fileUploadRepository.deleteAllById(ids);
    }
}