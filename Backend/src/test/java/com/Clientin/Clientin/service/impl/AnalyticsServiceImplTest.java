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
            entity.setMetricType(createTestMetricType());
        dto.setMetricType(entity.getMetricType());
        entity.setMetricKey("Test Metrickey");
        dto.setMetricKey(entity.getMetricKey());
        entity.setMetricValue(createTestBigDecimal());
        dto.setMetricValue(entity.getMetricValue());
        entity.setDateKey(createTestLocalDate());
        dto.setDateKey(entity.getDateKey());
        entity.setHourKey(42);
        dto.setHourKey(entity.getHourKey());
        entity.setEmployeeId("Test Employeeid");
        dto.setEmployeeId(entity.getEmployeeId());
        entity.setDepartment("Test Department");
        dto.setDepartment(entity.getDepartment());
        entity.setMetadata("Test Metadata");
        dto.setMetadata(entity.getMetadata());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setEmployee(createTestUser());
        dto.setEmployee(entity.getEmployee());
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
            assertThat(result.getMetricType()).isEqualTo(dto.getMetricType());
        assertThat(result.getMetricKey()).isEqualTo(dto.getMetricKey());
        assertThat(result.getMetricValue()).isEqualTo(dto.getMetricValue());
        assertThat(result.getDateKey()).isEqualTo(dto.getDateKey());
        assertThat(result.getHourKey()).isEqualTo(dto.getHourKey());
        assertThat(result.getEmployeeId()).isEqualTo(dto.getEmployeeId());
        assertThat(result.getDepartment()).isEqualTo(dto.getDepartment());
        assertThat(result.getMetadata()).isEqualTo(dto.getMetadata());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getEmployee()).isEqualTo(dto.getEmployee());
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
