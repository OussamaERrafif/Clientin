package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.ImprovementProposal;
import com.Clientin.Clientin.dto.ImprovementProposalDTO;
import com.Clientin.Clientin.mapper.ImprovementProposalMapper;
import com.Clientin.Clientin.repository.ImprovementProposalRepository;
import com.Clientin.Clientin.service.ImprovementProposalService;
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
public class ImprovementProposalServiceImpl implements ImprovementProposalService {

    private final ImprovementProposalRepository improvementProposalRepository;
    private final ImprovementProposalMapper improvementProposalMapper;

    @Override
    @Transactional
    public ImprovementProposalDTO create(ImprovementProposalDTO dto) {
        log.debug("Creating new ImprovementProposal");
        try {
            ImprovementProposal entity = improvementProposalMapper.toEntity(dto);
            return improvementProposalMapper.toDTO(improvementProposalRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImprovementProposalDTO> findById(String id) {
        log.debug("Fetching ImprovementProposal with ID: {}", id);
        return improvementProposalRepository.findById(id)
                .map(improvementProposalMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImprovementProposalDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged ImprovementProposal results");
        return improvementProposalRepository.findAll(pageable)
                .map(improvementProposalMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImprovementProposalDTO> findAll() {
        log.debug("Fetching all ImprovementProposal entities");
        return improvementProposalMapper.toDTOList(improvementProposalRepository.findAll());
    }

    @Override
    @Transactional
    public ImprovementProposalDTO update(String id, ImprovementProposalDTO dto) {
        log.debug("Updating ImprovementProposal with ID: {}", id);
        return improvementProposalRepository.findById(id)
                .map(existingEntity -> {
                    improvementProposalMapper.partialUpdate(dto, existingEntity);
                    return improvementProposalMapper.toDTO(improvementProposalRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "ImprovementProposal not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting ImprovementProposal with ID: {}", id);
        if (!improvementProposalRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "ImprovementProposal not found with id: " + id
            );
        }
        improvementProposalRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImprovementProposalDTO> search(Specification<ImprovementProposal> spec, Pageable pageable) {
        log.debug("Searching ImprovementProposal with specification");
        return improvementProposalRepository.findAll(spec, pageable)
                .map(improvementProposalMapper::toDTO);
    }

    @Override
    @Transactional
    public ImprovementProposalDTO partialUpdate(String id, ImprovementProposalDTO dto) {
        log.debug("Partial update for ImprovementProposal ID: {}", id);
        return improvementProposalRepository.findById(id)
                .map(entity -> {
                    improvementProposalMapper.partialUpdate(dto, entity);
                    return improvementProposalMapper.toDTO(improvementProposalRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "ImprovementProposal not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return improvementProposalRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<ImprovementProposalDTO> bulkCreate(List<ImprovementProposalDTO> dtos) {
        log.debug("Bulk creating ImprovementProposal entities: {} items", dtos.size());
        List<ImprovementProposal> entities = improvementProposalMapper.toEntityList(dtos);
        return improvementProposalMapper.toDTOList(improvementProposalRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting ImprovementProposal entities: {} items", ids.size());
        improvementProposalRepository.deleteAllById(ids);
    }
}