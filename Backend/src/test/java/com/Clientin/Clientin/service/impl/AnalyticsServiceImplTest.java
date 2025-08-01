package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.Analytics;
    import com.Clientin.Clientin.dto.AnalyticsDTO;
    import com.Clientin.Clientin.mapper.AnalyticsMapper;
    import com.Clientin.Clientin.repository.AnalyticsRepository;
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
    class AnalyticsServiceImplTest {

        @Mock
        private AnalyticsRepository analyticsRepository;
        @Mock
        private AnalyticsMapper analyticsMapper;
        @InjectMocks
        private AnalyticsServiceImpl service;

        private Analytics entity;
        private AnalyticsDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<Analytics> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new Analytics();
            dto = new AnalyticsDTO();
            entity.setMetricType(createTestMetricType());\n        dto.setMetricType(entity.getMetricType());\n        entity.setMetricKey("Test Metrickey");\n        dto.setMetricKey(entity.getMetricKey());\n        entity.setMetricValue(createTestBigDecimal());\n        dto.setMetricValue(entity.getMetricValue());\n        entity.setDateKey(createTestLocalDate());\n        dto.setDateKey(entity.getDateKey());\n        entity.setHourKey(42);\n        dto.setHourKey(entity.getHourKey());\n        entity.setEmployeeId("Test Employeeid");\n        dto.setEmployeeId(entity.getEmployeeId());\n        entity.setDepartment("Test Department");\n        dto.setDepartment(entity.getDepartment());\n        entity.setMetadata("Test Metadata");\n        dto.setMetadata(entity.getMetadata());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setEmployee(createTestUser());\n        dto.setEmployee(entity.getEmployee());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(analyticsMapper.toEntity(any())).willReturn(entity);
            given(analyticsRepository.save(any())).willReturn(entity);
            given(analyticsMapper.toDTO(any())).willReturn(dto);

            // Act
            AnalyticsDTO result = service.create(dto);

            // Assert
            assertThat(result.getMetricType()).isEqualTo(dto.getMetricType());\n        assertThat(result.getMetricKey()).isEqualTo(dto.getMetricKey());\n        assertThat(result.getMetricValue()).isEqualTo(dto.getMetricValue());\n        assertThat(result.getDateKey()).isEqualTo(dto.getDateKey());\n        assertThat(result.getHourKey()).isEqualTo(dto.getHourKey());\n        assertThat(result.getEmployeeId()).isEqualTo(dto.getEmployeeId());\n        assertThat(result.getDepartment()).isEqualTo(dto.getDepartment());\n        assertThat(result.getMetadata()).isEqualTo(dto.getMetadata());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getEmployee()).isEqualTo(dto.getEmployee());
            then(analyticsRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(analyticsRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(analyticsMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<AnalyticsDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(analyticsRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(analyticsRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("Analytics", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(analyticsRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(analyticsRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(analyticsRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<Analytics> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(analyticsRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(analyticsRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            AnalyticsDTO partialDto = new AnalyticsDTO();
            partialDto.setMetrictype(dto.getMetrictype());
            given(analyticsRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(analyticsMapper).should().partialUpdate(partialDto, entity);
            then(analyticsRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(analyticsRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(analyticsRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }