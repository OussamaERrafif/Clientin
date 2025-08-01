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
            entity.setEmployeeId("Test Employeeid");\n        dto.setEmployeeId(entity.getEmployeeId());\n        entity.setReviewerId("Test Reviewerid");\n        dto.setReviewerId(entity.getReviewerId());\n        entity.setReviewPeriodStart(createTestLocalDate());\n        dto.setReviewPeriodStart(entity.getReviewPeriodStart());\n        entity.setReviewPeriodEnd(createTestLocalDate());\n        dto.setReviewPeriodEnd(entity.getReviewPeriodEnd());\n        entity.setOverallScore(createTestBigDecimal());\n        dto.setOverallScore(entity.getOverallScore());\n        entity.setTechnicalSkillsScore(createTestBigDecimal());\n        dto.setTechnicalSkillsScore(entity.getTechnicalSkillsScore());\n        entity.setCommunicationScore(createTestBigDecimal());\n        dto.setCommunicationScore(entity.getCommunicationScore());\n        entity.setTeamworkScore(createTestBigDecimal());\n        dto.setTeamworkScore(entity.getTeamworkScore());\n        entity.setLeadershipScore(createTestBigDecimal());\n        dto.setLeadershipScore(entity.getLeadershipScore());\n        entity.setStrengths("Test Strengths");\n        dto.setStrengths(entity.getStrengths());\n        entity.setAreasForImprovement("Test Areasforimprovement");\n        dto.setAreasForImprovement(entity.getAreasForImprovement());\n        entity.setGoals("Test Goals");\n        dto.setGoals(entity.getGoals());\n        entity.setReviewerComments("Test Reviewercomments");\n        dto.setReviewerComments(entity.getReviewerComments());\n        entity.setEmployeeComments("Test Employeecomments");\n        dto.setEmployeeComments(entity.getEmployeeComments());\n        entity.setStatus(createTestReviewStatus());\n        dto.setStatus(entity.getStatus());\n        entity.setCompletedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCompletedAt(entity.getCompletedAt());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setUpdatedAt(entity.getUpdatedAt());\n        entity.setEmployee(createTestUser());\n        dto.setEmployee(entity.getEmployee());\n        entity.setReviewer(createTestUser());\n        dto.setReviewer(entity.getReviewer());
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
            assertThat(result.getEmployeeId()).isEqualTo(dto.getEmployeeId());\n        assertThat(result.getReviewerId()).isEqualTo(dto.getReviewerId());\n        assertThat(result.getReviewPeriodStart()).isEqualTo(dto.getReviewPeriodStart());\n        assertThat(result.getReviewPeriodEnd()).isEqualTo(dto.getReviewPeriodEnd());\n        assertThat(result.getOverallScore()).isEqualTo(dto.getOverallScore());\n        assertThat(result.getTechnicalSkillsScore()).isEqualTo(dto.getTechnicalSkillsScore());\n        assertThat(result.getCommunicationScore()).isEqualTo(dto.getCommunicationScore());\n        assertThat(result.getTeamworkScore()).isEqualTo(dto.getTeamworkScore());\n        assertThat(result.getLeadershipScore()).isEqualTo(dto.getLeadershipScore());\n        assertThat(result.getStrengths()).isEqualTo(dto.getStrengths());\n        assertThat(result.getAreasForImprovement()).isEqualTo(dto.getAreasForImprovement());\n        assertThat(result.getGoals()).isEqualTo(dto.getGoals());\n        assertThat(result.getReviewerComments()).isEqualTo(dto.getReviewerComments());\n        assertThat(result.getEmployeeComments()).isEqualTo(dto.getEmployeeComments());\n        assertThat(result.getStatus()).isEqualTo(dto.getStatus());\n        assertThat(result.getCompletedAt()).isEqualTo(dto.getCompletedAt());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());\n        assertThat(result.getEmployee()).isEqualTo(dto.getEmployee());\n        assertThat(result.getReviewer()).isEqualTo(dto.getReviewer());
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