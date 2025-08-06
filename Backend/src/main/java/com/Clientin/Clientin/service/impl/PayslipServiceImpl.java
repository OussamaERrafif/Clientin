package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Payslip;
import com.Clientin.Clientin.dto.PayslipDTO;
import com.Clientin.Clientin.mapper.PayslipMapper;
import com.Clientin.Clientin.repository.PayslipRepository;
import com.Clientin.Clientin.service.PayslipService;
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
public class PayslipServiceImpl implements PayslipService {

    private final PayslipRepository payslipRepository;
    private final PayslipMapper payslipMapper;

    @Override
    @Transactional
    public PayslipDTO create(PayslipDTO dto) {
        log.debug("Creating new Payslip");
        try {
            Payslip entity = payslipMapper.toEntity(dto);
            return payslipMapper.toDTO(payslipRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayslipDTO> findById(String id) {
        log.debug("Fetching Payslip with ID: {}", id);
        return payslipRepository.findById(id)
                .map(payslipMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayslipDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Payslip results");
        return payslipRepository.findAll(pageable)
                .map(payslipMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayslipDTO> findAll() {
        log.debug("Fetching all Payslip entities");
        return payslipMapper.toDTOList(payslipRepository.findAll());
    }

    @Override
    @Transactional
    public PayslipDTO update(String id, PayslipDTO dto) {
        log.debug("Updating Payslip with ID: {}", id);
        return payslipRepository.findById(id)
                .map(existingEntity -> {
                    payslipMapper.partialUpdate(dto, existingEntity);
                    return payslipMapper.toDTO(payslipRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Payslip not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Payslip with ID: {}", id);
        if (!payslipRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Payslip not found with id: " + id
            );
        }
        payslipRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayslipDTO> search(Specification<Payslip> spec, Pageable pageable) {
        log.debug("Searching Payslip with specification");
        return payslipRepository.findAll(spec, pageable)
                .map(payslipMapper::toDTO);
    }

    @Override
    @Transactional
    public PayslipDTO partialUpdate(String id, PayslipDTO dto) {
        log.debug("Partial update for Payslip ID: {}", id);
        return payslipRepository.findById(id)
                .map(entity -> {
                    payslipMapper.partialUpdate(dto, entity);
                    return payslipMapper.toDTO(payslipRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Payslip not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return payslipRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<PayslipDTO> bulkCreate(List<PayslipDTO> dtos) {
        log.debug("Bulk creating Payslip entities: {} items", dtos.size());
        List<Payslip> entities = payslipMapper.toEntityList(dtos);
        return payslipMapper.toDTOList(payslipRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Payslip entities: {} items", ids.size());
        payslipRepository.deleteAllById(ids);
    }
}