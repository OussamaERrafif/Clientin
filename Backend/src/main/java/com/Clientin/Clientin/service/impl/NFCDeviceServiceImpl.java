package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.NFCDevice;
import com.Clientin.Clientin.dto.NFCDeviceDTO;
import com.Clientin.Clientin.mapper.NFCDeviceMapper;
import com.Clientin.Clientin.repository.NFCDeviceRepository;
import com.Clientin.Clientin.service.NFCDeviceService;
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
public class NFCDeviceServiceImpl implements NFCDeviceService {

    private final NFCDeviceRepository nFCDeviceRepository;
    private final NFCDeviceMapper nFCDeviceMapper;

    @Override
    @Transactional
    public NFCDeviceDTO create(NFCDeviceDTO dto) {
        log.debug("Creating new NFCDevice");
        try {
            NFCDevice entity = nFCDeviceMapper.toEntity(dto);
            return nFCDeviceMapper.toDTO(nFCDeviceRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NFCDeviceDTO> findById(String id) {
        log.debug("Fetching NFCDevice with ID: {}", id);
        return nFCDeviceRepository.findById(id)
                .map(nFCDeviceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NFCDeviceDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged NFCDevice results");
        return nFCDeviceRepository.findAll(pageable)
                .map(nFCDeviceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NFCDeviceDTO> findAll() {
        log.debug("Fetching all NFCDevice entities");
        return nFCDeviceMapper.toDTOList(nFCDeviceRepository.findAll());
    }

    @Override
    @Transactional
    public NFCDeviceDTO update(String id, NFCDeviceDTO dto) {
        log.debug("Updating NFCDevice with ID: {}", id);
        return nFCDeviceRepository.findById(id)
                .map(existingEntity -> {
                    nFCDeviceMapper.partialUpdate(dto, existingEntity);
                    return nFCDeviceMapper.toDTO(nFCDeviceRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NFCDevice not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting NFCDevice with ID: {}", id);
        if (!nFCDeviceRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "NFCDevice not found with id: " + id
            );
        }
        nFCDeviceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NFCDeviceDTO> search(Specification<NFCDevice> spec, Pageable pageable) {
        log.debug("Searching NFCDevice with specification");
        return nFCDeviceRepository.findAll(spec, pageable)
                .map(nFCDeviceMapper::toDTO);
    }

    @Override
    @Transactional
    public NFCDeviceDTO partialUpdate(String id, NFCDeviceDTO dto) {
        log.debug("Partial update for NFCDevice ID: {}", id);
        return nFCDeviceRepository.findById(id)
                .map(entity -> {
                    nFCDeviceMapper.partialUpdate(dto, entity);
                    return nFCDeviceMapper.toDTO(nFCDeviceRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "NFCDevice not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return nFCDeviceRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<NFCDeviceDTO> bulkCreate(List<NFCDeviceDTO> dtos) {
        log.debug("Bulk creating NFCDevice entities: {} items", dtos.size());
        List<NFCDevice> entities = nFCDeviceMapper.toEntityList(dtos);
        return nFCDeviceMapper.toDTOList(nFCDeviceRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting NFCDevice entities: {} items", ids.size());
        nFCDeviceRepository.deleteAllById(ids);
    }
}