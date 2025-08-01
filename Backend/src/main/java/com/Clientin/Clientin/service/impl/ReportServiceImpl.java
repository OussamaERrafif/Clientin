package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Report;
import com.Clientin.Clientin.dto.ReportDTO;
import com.Clientin.Clientin.mapper.ReportMapper;
import com.Clientin.Clientin.repository.ReportRepository;
import com.Clientin.Clientin.service.ReportService;
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
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    @Transactional
    public ReportDTO create(ReportDTO dto) {
        log.debug("Creating new Report");
        try {
            Report entity = reportMapper.toEntity(dto);
            return reportMapper.toDTO(reportRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDTO> findById(String id) {
        log.debug("Fetching Report with ID: {}", id);
        return reportRepository.findById(id)
                .map(reportMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Report results");
        return reportRepository.findAll(pageable)
                .map(reportMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDTO> findAll() {
        log.debug("Fetching all Report entities");
        return reportMapper.toDTOList(reportRepository.findAll());
    }

    @Override
    @Transactional
    public ReportDTO update(String id, ReportDTO dto) {
        log.debug("Updating Report with ID: {}", id);
        return reportRepository.findById(id)
                .map(existingEntity -> {
                    reportMapper.partialUpdate(dto, existingEntity);
                    return reportMapper.toDTO(reportRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Report not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Report with ID: {}", id);
        if (!reportRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Report not found with id: " + id
            );
        }
        reportRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> search(Specification<Report> spec, Pageable pageable) {
        log.debug("Searching Report with specification");
        return reportRepository.findAll(spec, pageable)
                .map(reportMapper::toDTO);
    }

    @Override
    @Transactional
    public ReportDTO partialUpdate(String id, ReportDTO dto) {
        log.debug("Partial update for Report ID: {}", id);
        return reportRepository.findById(id)
                .map(entity -> {
                    reportMapper.partialUpdate(dto, entity);
                    return reportMapper.toDTO(reportRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Report not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return reportRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<ReportDTO> bulkCreate(List<ReportDTO> dtos) {
        log.debug("Bulk creating Report entities: {} items", dtos.size());
        List<Report> entities = reportMapper.toEntityList(dtos);
        return reportMapper.toDTOList(reportRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Report entities: {} items", ids.size());
        reportRepository.deleteAllById(ids);
    }
}