package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Absence;
import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.dto.AbsenceDTO;
import com.Clientin.Clientin.mapper.AbsenceMapper;
import com.Clientin.Clientin.repository.AbsenceRepository;
import com.Clientin.Clientin.repository.UserRepository;
import com.Clientin.Clientin.service.AbsenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final UserRepository userRepository;
    private final AbsenceMapper absenceMapper;

    @Override
    @Transactional
    public AbsenceDTO create(AbsenceDTO dto) {
        log.debug("Creating new Absence for employee: {}", dto.getEmployeeId());
        
        // Validate employee exists
        if (!userRepository.existsById(dto.getEmployeeId())) {
            throw new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId());
        }
        
        // Check for conflicting absences
        if (hasConflictingAbsence(dto.getEmployeeId(), dto.getStartDate(), dto.getEndDate())) {
            throw new IllegalArgumentException("Conflicting absence already exists for this period");
        }
        
        // Calculate working days if not provided
        if (dto.getDaysCount() == null) {
            dto.setDaysCount(calculateAbsenceDays(dto.getStartDate(), dto.getEndDate()));
        }
        
        try {
            Absence entity = absenceMapper.toEntity(dto);
            entity.setStatus(Absence.AbsenceStatus.PENDING);
            return absenceMapper.toDTO(absenceRepository.save(entity));
        } catch (Exception e) {
            log.error("Error creating absence", e);
            throw new RuntimeException("Error creating absence", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceDTO> findById(String id) {
        log.debug("Fetching Absence with ID: {}", id);
        return absenceRepository.findById(id)
                .map(absenceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Absence results");
        return absenceRepository.findAll(pageable)
                .map(absenceMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findAll() {
        log.debug("Fetching all Absence entities");
        return absenceMapper.toDTOList(absenceRepository.findAll());
    }

    @Override
    @Transactional
    public AbsenceDTO update(String id, AbsenceDTO dto) {
        log.debug("Updating Absence with ID: {}", id);
        return absenceRepository.findById(id)
                .map(existingEntity -> {
                    // Don't allow updating approved absences
                    if (existingEntity.isApproved()) {
                        throw new IllegalStateException("Cannot update approved absence");
                    }
                    
                    absenceMapper.partialUpdate(dto, existingEntity);
                    return absenceMapper.toDTO(absenceRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Absence with ID: {}", id);
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
        
        // Don't allow deleting approved absences
        if (absence.isApproved()) {
            throw new IllegalStateException("Cannot delete approved absence");
        }
        
        absenceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> search(Specification<Absence> spec, Pageable pageable) {
        log.debug("Searching Absence with specification");
        return absenceRepository.findAll(spec, pageable)
                .map(absenceMapper::toDTO);
    }

    @Override
    @Transactional
    public AbsenceDTO partialUpdate(String id, AbsenceDTO dto) {
        log.debug("Partial update for Absence ID: {}", id);
        return absenceRepository.findById(id)
                .map(entity -> {
                    if (entity.isApproved()) {
                        throw new IllegalStateException("Cannot update approved absence");
                    }
                    absenceMapper.partialUpdate(dto, entity);
                    return absenceMapper.toDTO(absenceRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return absenceRepository.existsById(id);
    }

    @Override
    @Transactional
    public AbsenceDTO approve(String id, String approverId) {
        log.debug("Approving absence ID: {} by user: {}", id, approverId);
        
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new EntityNotFoundException("Approver not found with id: " + approverId));
        
        return absenceRepository.findById(id)
                .map(absence -> {
                    if (!absence.isPending()) {
                        throw new IllegalStateException("Only pending absences can be approved");
                    }
                    
                    absence.approve(approver);
                    return absenceMapper.toDTO(absenceRepository.save(absence));
                })
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
    }

    @Override
    @Transactional
    public AbsenceDTO reject(String id, String approverId, String reason) {
        log.debug("Rejecting absence ID: {} by user: {}", id, approverId);
        
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new EntityNotFoundException("Approver not found with id: " + approverId));
        
        return absenceRepository.findById(id)
                .map(absence -> {
                    if (!absence.isPending()) {
                        throw new IllegalStateException("Only pending absences can be rejected");
                    }
                    
                    absence.reject(approver, reason);
                    return absenceMapper.toDTO(absenceRepository.save(absence));
                })
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findByEmployeeId(String employeeId) {
        log.debug("Finding absences for employee: {}", employeeId);
        return absenceMapper.toDTOList(absenceRepository.findByEmployeeId(employeeId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findPendingApprovals() {
        log.debug("Finding pending absence approvals");
        return absenceMapper.toDTOList(
            absenceRepository.findByStatus(Absence.AbsenceStatus.PENDING)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findByDateRange(LocalDate startDate, LocalDate endDate) {
        log.debug("Finding absences between {} and {}", startDate, endDate);
        return absenceRepository.findAll().stream()
                .filter(absence -> !absence.getEndDate().isBefore(startDate) && 
                                 !absence.getStartDate().isAfter(endDate))
                .map(absenceMapper::toDTO)
                .toList();
    }

    @Override
    public int calculateAbsenceDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        
        // Calculate working days (excluding weekends)
        return (int) Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && 
                              date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasConflictingAbsence(String employeeId, LocalDate startDate, LocalDate endDate) {
        List<Absence> existingAbsences = absenceRepository.findByEmployeeId(employeeId);
        
        return existingAbsences.stream()
                .filter(absence -> absence.getStatus() != Absence.AbsenceStatus.REJECTED && 
                                 absence.getStatus() != Absence.AbsenceStatus.CANCELLED)
                .anyMatch(absence -> 
                    !endDate.isBefore(absence.getStartDate()) && 
                    !startDate.isAfter(absence.getEndDate())
                );
    }

    @Override
    @Transactional
    public List<AbsenceDTO> bulkCreate(List<AbsenceDTO> dtos) {
        log.debug("Bulk creating Absence entities: {} items", dtos.size());
        List<Absence> entities = absenceMapper.toEntityList(dtos);
        return absenceMapper.toDTOList(absenceRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Absence entities: {} items", ids.size());
        absenceRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public List<AbsenceDTO> bulkApprove(List<String> ids, String approverId) {
        log.debug("Bulk approving {} absences by user: {}", ids.size(), approverId);
        
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new EntityNotFoundException("Approver not found with id: " + approverId));
        
        List<Absence> absences = absenceRepository.findAllById(ids);
        
        absences.forEach(absence -> {
            if (absence.isPending()) {
                absence.approve(approver);
            }
        });
        
        return absenceMapper.toDTOList(absenceRepository.saveAll(absences));
    }
}