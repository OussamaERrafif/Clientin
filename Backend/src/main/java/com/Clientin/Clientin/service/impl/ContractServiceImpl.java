package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Contract;
import com.Clientin.Clientin.dto.ContractDTO;
import com.Clientin.Clientin.mapper.ContractMapper;
import com.Clientin.Clientin.repository.ContractRepository;
import com.Clientin.Clientin.service.ContractService;
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
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    @Transactional
    public ContractDTO create(ContractDTO dto) {
        log.debug("Creating new Contract");
        try {
            Contract entity = contractMapper.toEntity(dto);
            return contractMapper.toDTO(contractRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContractDTO> findById(String id) {
        log.debug("Fetching Contract with ID: {}", id);
        return contractRepository.findById(id)
                .map(contractMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Contract results");
        return contractRepository.findAll(pageable)
                .map(contractMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractDTO> findAll() {
        log.debug("Fetching all Contract entities");
        return contractMapper.toDTOList(contractRepository.findAll());
    }

    @Override
    @Transactional
    public ContractDTO update(String id, ContractDTO dto) {
        log.debug("Updating Contract with ID: {}", id);
        return contractRepository.findById(id)
                .map(existingEntity -> {
                    contractMapper.partialUpdate(dto, existingEntity);
                    return contractMapper.toDTO(contractRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Contract not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Contract with ID: {}", id);
        if (!contractRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Contract not found with id: " + id
            );
        }
        contractRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractDTO> search(Specification<Contract> spec, Pageable pageable) {
        log.debug("Searching Contract with specification");
        return contractRepository.findAll(spec, pageable)
                .map(contractMapper::toDTO);
    }

    @Override
    @Transactional
    public ContractDTO partialUpdate(String id, ContractDTO dto) {
        log.debug("Partial update for Contract ID: {}", id);
        return contractRepository.findById(id)
                .map(entity -> {
                    contractMapper.partialUpdate(dto, entity);
                    return contractMapper.toDTO(contractRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Contract not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return contractRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<ContractDTO> bulkCreate(List<ContractDTO> dtos) {
        log.debug("Bulk creating Contract entities: {} items", dtos.size());
        List<Contract> entities = contractMapper.toEntityList(dtos);
        return contractMapper.toDTOList(contractRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Contract entities: {} items", ids.size());
        contractRepository.deleteAllById(ids);
    }
}