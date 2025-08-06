package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.PerformanceReview;
    import com.Clientin.Clientin.dto.PerformanceReviewDTO;
    import com.Clientin.Clientin.mapper.PerformanceReviewMapper;
    import com.Clientin.Clientin.repository.PerformanceReviewRepository;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.*;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.dao.*;
    import org.springframework.data.domain.*;
    import java.util.*;
    import static org.assertj.core.api.Assertions.*;
    import static org.mockito.ArgumentMatchers.*;
    import static org.mockito.BDDMockito.*;

    @ExtendWith(MockitoExtension.class)
    class PerformanceReviewServiceImplTest {

        @Mock
        private PerformanceReviewRepository performanceReviewRepository;
        @Mock
        private PerformanceReviewMapper performanceReviewMapper;
        @InjectMocks
        private PerformanceReviewServiceImpl service;

        private PerformanceReview entity;
        private PerformanceReviewDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<PerformanceReview> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new PerformanceReview();
            dto = new PerformanceReviewDTO();
            entity.setEmployeeId("Test Employeeid");
        dto.setEmployeeId(entity.getEmployeeId());
        entity.setReviewerId("Test Reviewerid");
        dto.setReviewerId(entity.getReviewerId());
        entity.setReviewPeriodStart(createTestLocalDate());
        dto.setReviewPeriodStart(entity.getReviewPeriodStart());
        entity.setReviewPeriodEnd(createTestLocalDate());
        dto.setReviewPeriodEnd(entity.getReviewPeriodEnd());
        entity.setOverallScore(createTestBigDecimal());
        dto.setOverallScore(entity.getOverallScore());
        entity.setTechnicalSkillsScore(createTestBigDecimal());
        dto.setTechnicalSkillsScore(entity.getTechnicalSkillsScore());
        entity.setCommunicationScore(createTestBigDecimal());
        dto.setCommunicationScore(entity.getCommunicationScore());
        entity.setTeamworkScore(createTestBigDecimal());
        dto.setTeamworkScore(entity.getTeamworkScore());
        entity.setLeadershipScore(createTestBigDecimal());
        dto.setLeadershipScore(entity.getLeadershipScore());
        entity.setStrengths("Test Strengths");
        dto.setStrengths(entity.getStrengths());
        entity.setAreasForImprovement("Test Areasforimprovement");
        dto.setAreasForImprovement(entity.getAreasForImprovement());
        entity.setGoals("Test Goals");
        dto.setGoals(entity.getGoals());
        entity.setReviewerComments("Test Reviewercomments");
        dto.setReviewerComments(entity.getReviewerComments());
        entity.setEmployeeComments("Test Employeecomments");
        dto.setEmployeeComments(entity.getEmployeeComments());
        entity.setStatus(createTestReviewStatus());
        dto.setStatus(entity.getStatus());
        entity.setCompletedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCompletedAt(entity.getCompletedAt());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setUpdatedAt(entity.getUpdatedAt());
        entity.setEmployee(createTestUser());
        dto.setEmployee(entity.getEmployee());
        entity.setReviewer(createTestUser());
        dto.setReviewer(entity.getReviewer());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(performanceReviewMapper.toEntity(any())).willReturn(entity);
            given(performanceReviewRepository.save(any())).willReturn(entity);
            given(performanceReviewMapper.toDTO(any())).willReturn(dto);

            // Act
            PerformanceReviewDTO result = service.create(dto);

            // Assert
            assertThat(result.getEmployeeId()).isEqualTo(dto.getEmployeeId());
        assertThat(result.getReviewerId()).isEqualTo(dto.getReviewerId());
        assertThat(result.getReviewPeriodStart()).isEqualTo(dto.getReviewPeriodStart());
        assertThat(result.getReviewPeriodEnd()).isEqualTo(dto.getReviewPeriodEnd());
        assertThat(result.getOverallScore()).isEqualTo(dto.getOverallScore());
        assertThat(result.getTechnicalSkillsScore()).isEqualTo(dto.getTechnicalSkillsScore());
        assertThat(result.getCommunicationScore()).isEqualTo(dto.getCommunicationScore());
        assertThat(result.getTeamworkScore()).isEqualTo(dto.getTeamworkScore());
        assertThat(result.getLeadershipScore()).isEqualTo(dto.getLeadershipScore());
        assertThat(result.getStrengths()).isEqualTo(dto.getStrengths());
        assertThat(result.getAreasForImprovement()).isEqualTo(dto.getAreasForImprovement());
        assertThat(result.getGoals()).isEqualTo(dto.getGoals());
        assertThat(result.getReviewerComments()).isEqualTo(dto.getReviewerComments());
        assertThat(result.getEmployeeComments()).isEqualTo(dto.getEmployeeComments());
        assertThat(result.getStatus()).isEqualTo(dto.getStatus());
        assertThat(result.getCompletedAt()).isEqualTo(dto.getCompletedAt());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
        assertThat(result.getEmployee()).isEqualTo(dto.getEmployee());
        assertThat(result.getReviewer()).isEqualTo(dto.getReviewer());
            then(performanceReviewRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(performanceReviewRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(performanceReviewMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<PerformanceReviewDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(performanceReviewRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(performanceReviewRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("PerformanceReview", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(performanceReviewRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(performanceReviewRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(performanceReviewRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<PerformanceReview> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(performanceReviewRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(performanceReviewRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            PerformanceReviewDTO partialDto = new PerformanceReviewDTO();
            partialDto.setEmployeeid(dto.getEmployeeid());
            given(performanceReviewRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(performanceReviewMapper).should().partialUpdate(partialDto, entity);
            then(performanceReviewRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(performanceReviewRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(performanceReviewRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
