package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.EmailTemplate;
import com.Clientin.Clientin.dto.EmailTemplateDTO;
import com.Clientin.Clientin.mapper.EmailTemplateMapper;
import com.Clientin.Clientin.repository.EmailTemplateRepository;
import com.Clientin.Clientin.service.EmailTemplateService;
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
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    @Transactional
    public EmailTemplateDTO create(EmailTemplateDTO dto) {
        log.debug("Creating new EmailTemplate");
        try {
            EmailTemplate entity = emailTemplateMapper.toEntity(dto);
            return emailTemplateMapper.toDTO(emailTemplateRepository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailTemplateDTO> findById(String id) {
        log.debug("Fetching EmailTemplate with ID: {}", id);
        return emailTemplateRepository.findById(id)
                .map(emailTemplateMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailTemplateDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged EmailTemplate results");
        return emailTemplateRepository.findAll(pageable)
                .map(emailTemplateMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailTemplateDTO> findAll() {
        log.debug("Fetching all EmailTemplate entities");
        return emailTemplateMapper.toDTOList(emailTemplateRepository.findAll());
    }

    @Override
    @Transactional
    public EmailTemplateDTO update(String id, EmailTemplateDTO dto) {
        log.debug("Updating EmailTemplate with ID: {}", id);
        return emailTemplateRepository.findById(id)
                .map(existingEntity -> {
                    emailTemplateMapper.partialUpdate(dto, existingEntity);
                    return emailTemplateMapper.toDTO(emailTemplateRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EmailTemplate not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting EmailTemplate with ID: {}", id);
        if (!emailTemplateRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "EmailTemplate not found with id: " + id
            );
        }
        emailTemplateRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailTemplateDTO> search(Specification<EmailTemplate> spec, Pageable pageable) {
        log.debug("Searching EmailTemplate with specification");
        return emailTemplateRepository.findAll(spec, pageable)
                .map(emailTemplateMapper::toDTO);
    }

    @Override
    @Transactional
    public EmailTemplateDTO partialUpdate(String id, EmailTemplateDTO dto) {
        log.debug("Partial update for EmailTemplate ID: {}", id);
        return emailTemplateRepository.findById(id)
                .map(entity -> {
                    emailTemplateMapper.partialUpdate(dto, entity);
                    return emailTemplateMapper.toDTO(emailTemplateRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "EmailTemplate not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return emailTemplateRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<EmailTemplateDTO> bulkCreate(List<EmailTemplateDTO> dtos) {
        log.debug("Bulk creating EmailTemplate entities: {} items", dtos.size());
        List<EmailTemplate> entities = emailTemplateMapper.toEntityList(dtos);
        return emailTemplateMapper.toDTOList(emailTemplateRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting EmailTemplate entities: {} items", ids.size());
        emailTemplateRepository.deleteAllById(ids);
    }
}