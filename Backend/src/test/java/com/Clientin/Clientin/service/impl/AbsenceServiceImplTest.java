package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.dto.AbsenceDTO;
import com.Clientin.Clientin.entity.Absence;
import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.mapper.AbsenceMapper;
import com.Clientin.Clientin.repository.AbsenceRepository;
import com.Clientin.Clientin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AbsenceServiceImplTest {

    @Mock
    private AbsenceRepository absenceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AbsenceMapper absenceMapper;

    @InjectMocks
    private AbsenceServiceImpl absenceService;

    private User testEmployee;
    private User testManager;
    private Absence testAbsence;
    private AbsenceDTO testAbsenceDTO;

    @BeforeEach
    void setUp() {
        testEmployee = User.builder()
                .id("emp123")
                .name("John Doe")
                .email("john.doe@example.com")
                .role(User.Role.EMPLOYEE)
                .build();

        testManager = User.builder()
                .id("mgr123")
                .name("Jane Manager")
                .email("jane.manager@example.com")
                .role(User.Role.MANAGER)
                .build();

        testAbsence = Absence.builder()
                .id("abs123")
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .status(Absence.AbsenceStatus.PENDING)
                .isPaid(true)
                .build();

        testAbsenceDTO = AbsenceDTO.builder()
                .id("abs123")
                .employeeId("emp123")
                .absenceType(AbsenceDTO.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .status(AbsenceDTO.AbsenceStatus.PENDING)
                .isPaid(true)
                .build();
    }

    @Test
    void create_ShouldReturnAbsenceDTO_WhenValidInput() {
        // Given
        given(userRepository.existsById(testAbsenceDTO.getEmployeeId())).willReturn(true);
        given(absenceRepository.findByEmployeeId(anyString())).willReturn(List.of());
        given(absenceMapper.toEntity(any(AbsenceDTO.class))).willReturn(testAbsence);
        given(absenceRepository.save(any(Absence.class))).willReturn(testAbsence);
        given(absenceMapper.toDTO(any(Absence.class))).willReturn(testAbsenceDTO);

        // When
        AbsenceDTO result = absenceService.create(testAbsenceDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmployeeId()).isEqualTo(testAbsenceDTO.getEmployeeId());
        assertThat(result.getAbsenceType()).isEqualTo(testAbsenceDTO.getAbsenceType());
        
        then(userRepository).should().existsById(testAbsenceDTO.getEmployeeId());
        then(absenceRepository).should().save(any(Absence.class));
    }

    @Test
    void create_ShouldThrowException_WhenEmployeeNotExists() {
        // Given
        given(userRepository.existsById(testAbsenceDTO.getEmployeeId())).willReturn(false);

        // When & Then
        assertThatThrownBy(() -> absenceService.create(testAbsenceDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Employee not found");
        
        then(absenceRepository).should(never()).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenConflictingAbsence() {
        // Given
        given(userRepository.existsById(testAbsenceDTO.getEmployeeId())).willReturn(true);
        
        Absence conflictingAbsence = Absence.builder()
                .startDate(LocalDate.now().plusDays(8))
                .endDate(LocalDate.now().plusDays(12))
                .status(Absence.AbsenceStatus.APPROVED)
                .build();
        
        given(absenceRepository.findByEmployeeId(anyString())).willReturn(List.of(conflictingAbsence));

        // When & Then
        assertThatThrownBy(() -> absenceService.create(testAbsenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Conflicting absence");
    }

    @Test
    void findById_ShouldReturnAbsenceDTO_WhenExists() {
        // Given
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));
        given(absenceMapper.toDTO(testAbsence)).willReturn(testAbsenceDTO);

        // When
        Optional<AbsenceDTO> result = absenceService.findById("abs123");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("abs123");
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        // Given
        given(absenceRepository.findById("nonexistent")).willReturn(Optional.empty());

        // When
        Optional<AbsenceDTO> result = absenceService.findById("nonexistent");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void approve_ShouldUpdateStatus_WhenPendingAbsence() {
        // Given
        testAbsence.setStatus(Absence.AbsenceStatus.PENDING);
        
        given(userRepository.findById("mgr123")).willReturn(Optional.of(testManager));
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));
        given(absenceRepository.save(any(Absence.class))).willReturn(testAbsence);
        given(absenceMapper.toDTO(any(Absence.class))).willReturn(testAbsenceDTO);

        // When
        AbsenceDTO result = absenceService.approve("abs123", "mgr123");

        // Then
        assertThat(result).isNotNull();
        then(absenceRepository).should().save(testAbsence);
        assertThat(testAbsence.getStatus()).isEqualTo(Absence.AbsenceStatus.APPROVED);
        assertThat(testAbsence.getApprover()).isEqualTo(testManager);
        assertThat(testAbsence.getApprovedAt()).isNotNull();
    }

    @Test
    void approve_ShouldThrowException_WhenAbsenceNotPending() {
        // Given
        testAbsence.setStatus(Absence.AbsenceStatus.APPROVED); // Already approved
        
        given(userRepository.findById("mgr123")).willReturn(Optional.of(testManager));
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));

        // When & Then
        assertThatThrownBy(() -> absenceService.approve("abs123", "mgr123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Only pending absences can be approved");
    }

    @Test
    void reject_ShouldUpdateStatusAndReason_WhenPendingAbsence() {
        // Given
        String rejectionReason = "Business requirements";
        testAbsence.setStatus(Absence.AbsenceStatus.PENDING);
        
        given(userRepository.findById("mgr123")).willReturn(Optional.of(testManager));
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));
        given(absenceRepository.save(any(Absence.class))).willReturn(testAbsence);
        given(absenceMapper.toDTO(any(Absence.class))).willReturn(testAbsenceDTO);

        // When
        AbsenceDTO result = absenceService.reject("abs123", "mgr123", rejectionReason);

        // Then
        assertThat(result).isNotNull();
        then(absenceRepository).should().save(testAbsence);
        assertThat(testAbsence.getStatus()).isEqualTo(Absence.AbsenceStatus.REJECTED);
        assertThat(testAbsence.getApprover()).isEqualTo(testManager);
        assertThat(testAbsence.getComments()).isEqualTo(rejectionReason);
    }

    @Test
    void update_ShouldThrowException_WhenAbsenceApproved() {
        // Given
        testAbsence.setStatus(Absence.AbsenceStatus.APPROVED);
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));

        // When & Then
        assertThatThrownBy(() -> absenceService.update("abs123", testAbsenceDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot update approved absence");
    }

    @Test
    void delete_ShouldThrowException_WhenAbsenceApproved() {
        // Given
        testAbsence.setStatus(Absence.AbsenceStatus.APPROVED);
        given(absenceRepository.findById("abs123")).willReturn(Optional.of(testAbsence));

        // When & Then
        assertThatThrownBy(() -> absenceService.delete("abs123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot delete approved absence");
    }

    @Test
    void calculateAbsenceDays_ShouldExcludeWeekends() {
        // Given - Monday to Friday (5 working days)
        LocalDate monday = LocalDate.of(2024, 12, 23); // Monday
        LocalDate friday = LocalDate.of(2024, 12, 27); // Friday

        // When
        int workingDays = absenceService.calculateAbsenceDays(monday, friday);

        // Then
        assertThat(workingDays).isEqualTo(5); // Mon, Tue, Wed, Thu, Fri
    }

    @Test
    void calculateAbsenceDays_ShouldReturnZero_WhenNullDates() {
        // When
        int workingDays = absenceService.calculateAbsenceDays(null, null);

        // Then
        assertThat(workingDays).isEqualTo(0);
    }

    @Test
    void hasConflictingAbsence_ShouldReturnTrue_WhenOverlapping() {
        // Given
        LocalDate newStart = LocalDate.now().plusDays(8);
        LocalDate newEnd = LocalDate.now().plusDays(12);
        
        Absence existingAbsence = Absence.builder()
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .status(Absence.AbsenceStatus.APPROVED)
                .build();
        
        given(absenceRepository.findByEmployeeId("emp123")).willReturn(List.of(existingAbsence));

        // When
        boolean hasConflict = absenceService.hasConflictingAbsence("emp123", newStart, newEnd);

        // Then
        assertThat(hasConflict).isTrue();
    }

    @Test
    void hasConflictingAbsence_ShouldReturnFalse_WhenNoOverlap() {
        // Given
        LocalDate newStart = LocalDate.now().plusDays(15);
        LocalDate newEnd = LocalDate.now().plusDays(18);
        
        Absence existingAbsence = Absence.builder()
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .status(Absence.AbsenceStatus.APPROVED)
                .build();
        
        given(absenceRepository.findByEmployeeId("emp123")).willReturn(List.of(existingAbsence));

        // When
        boolean hasConflict = absenceService.hasConflictingAbsence("emp123", newStart, newEnd);

        // Then
        assertThat(hasConflict).isFalse();
    }

    @Test
    void hasConflictingAbsence_ShouldReturnFalse_WhenExistingIsRejected() {
        // Given
        LocalDate newStart = LocalDate.now().plusDays(8);
        LocalDate newEnd = LocalDate.now().plusDays(12);
        
        Absence rejectedAbsence = Absence.builder()
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .status(Absence.AbsenceStatus.REJECTED) // Rejected absences don't conflict
                .build();
        
        given(absenceRepository.findByEmployeeId("emp123")).willReturn(List.of(rejectedAbsence));

        // When
        boolean hasConflict = absenceService.hasConflictingAbsence("emp123", newStart, newEnd);

        // Then
        assertThat(hasConflict).isFalse();
    }

    @Test
    void findPendingApprovals_ShouldReturnPendingAbsences() {
        // Given
        List<Absence> pendingAbsences = List.of(testAbsence);
        given(absenceRepository.findByStatus(Absence.AbsenceStatus.PENDING)).willReturn(pendingAbsences);
        given(absenceMapper.toDTOList(pendingAbsences)).willReturn(List.of(testAbsenceDTO));

        // When
        List<AbsenceDTO> result = absenceService.findPendingApprovals();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(AbsenceDTO.AbsenceStatus.PENDING);
    }

    @Test
    void bulkApprove_ShouldApproveMultipleAbsences() {
        // Given
        List<String> absenceIds = List.of("abs1", "abs2");
        List<Absence> absences = List.of(
            Absence.builder().id("abs1").status(Absence.AbsenceStatus.PENDING).build(),
            Absence.builder().id("abs2").status(Absence.AbsenceStatus.PENDING).build()
        );
        
        given(userRepository.findById("mgr123")).willReturn(Optional.of(testManager));
        given(absenceRepository.findAllById(absenceIds)).willReturn(absences);
        given(absenceRepository.saveAll(any())).willReturn(absences);
        given(absenceMapper.toDTOList(any())).willReturn(List.of(testAbsenceDTO, testAbsenceDTO));

        // When
        List<AbsenceDTO> result = absenceService.bulkApprove(absenceIds, "mgr123");

        // Then
        assertThat(result).hasSize(2);
        then(absenceRepository).should().saveAll(absences);
        
        // Verify all absences were approved
        absences.forEach(absence -> {
            assertThat(absence.getStatus()).isEqualTo(Absence.AbsenceStatus.APPROVED);
            assertThat(absence.getApprover()).isEqualTo(testManager);
        });
    }
}
