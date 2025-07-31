package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.EmployeeProfile;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.dto.EmployeeProfileDTO;
import com.Clientin.Clientin.mapper.EmployeeProfileMapper;
import com.Clientin.Clientin.repository.EmployeeProfileRepository;
import com.Clientin.Clientin.service.EmployeeProfileService;
import com.Clientin.Clientin.service.LogService;
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
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final EmployeeProfileMapper employeeProfileMapper;
    private final LogService logService;

    @Override
    @Transactional
    public EmployeeProfileDTO create(EmployeeProfileDTO dto) {
        log.debug("Creating new EmployeeProfile");
        try {
            EmployeeProfile entity = employeeProfileMapper.toEntity(dto);
            return employeeProfileMapper.toDTO(employeeProfileRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeProfileDTO> findById(String id) {
        log.debug("Fetching EmployeeProfile with ID: {}", id);
        return employeeProfileRepository.findById(id)
                .map(employeeProfileMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeProfileDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged EmployeeProfile results");
        return employeeProfileRepository.findAll(pageable)
                .map(employeeProfileMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeProfileDTO> findAll() {
        log.debug("Fetching all EmployeeProfile entities");
        return employeeProfileMapper.toDTOList(employeeProfileRepository.findAll());
    }

    @Override
    @Transactional
    public EmployeeProfileDTO update(String id, EmployeeProfileDTO dto) {
        log.debug("Updating EmployeeProfile with ID: {}", id);
        return employeeProfileRepository.findById(id)
                .map(existingEntity -> {
                    employeeProfileMapper.partialUpdate(dto, existingEntity);
                    return employeeProfileMapper.toDTO(employeeProfileRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EmployeeProfile not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting EmployeeProfile with ID: {}", id);
        if (!employeeProfileRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "EmployeeProfile not found with id: " + id
            );
        }
        employeeProfileRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeProfileDTO> search(Specification<EmployeeProfile> spec, Pageable pageable) {
        log.debug("Searching EmployeeProfile with specification");
        return employeeProfileRepository.findAll(spec, pageable)
                .map(employeeProfileMapper::toDTO);
    }

    @Override
    @Transactional
    public EmployeeProfileDTO partialUpdate(String id, EmployeeProfileDTO dto) {
        log.debug("Partial update for EmployeeProfile ID: {}", id);
        return employeeProfileRepository.findById(id)
                .map(entity -> {
                    employeeProfileMapper.partialUpdate(dto, entity);
                    return employeeProfileMapper.toDTO(employeeProfileRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EmployeeProfile not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return employeeProfileRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<EmployeeProfileDTO> bulkCreate(List<EmployeeProfileDTO> dtos) {
        log.debug("Bulk creating EmployeeProfile entities: {} items", dtos.size());
        List<EmployeeProfile> entities = employeeProfileMapper.toEntityList(dtos);
        return employeeProfileMapper.toDTOList(employeeProfileRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting EmployeeProfile entities: {} items", ids.size());
        employeeProfileRepository.deleteAllById(ids);
    }
}