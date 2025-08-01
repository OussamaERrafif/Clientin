package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.Report;
    import com.Clientin.Clientin.dto.ReportDTO;
    import com.Clientin.Clientin.mapper.ReportMapper;
    import com.Clientin.Clientin.repository.ReportRepository;
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
    class ReportServiceImplTest {

        @Mock
        private ReportRepository reportRepository;
        @Mock
        private ReportMapper reportMapper;
        @InjectMocks
        private ReportServiceImpl service;

        private Report entity;
        private ReportDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<Report> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new Report();
            dto = new ReportDTO();
            entity.setUserId("Test Userid");\n        dto.setUserId(entity.getUserId());\n        entity.setReportName("Test Reportname");\n        dto.setReportName(entity.getReportName());\n        entity.setReportType(createTestReportType());\n        dto.setReportType(entity.getReportType());\n        entity.setStatus(createTestReportStatus());\n        dto.setStatus(entity.getStatus());\n        entity.setParameters("Test Parameters");\n        dto.setParameters(entity.getParameters());\n        entity.setFilePath("Test Filepath");\n        dto.setFilePath(entity.getFilePath());\n        entity.setFileSize(createTestLong());\n        dto.setFileSize(entity.getFileSize());\n        entity.setFormat(createTestReportFormat());\n        dto.setFormat(entity.getFormat());\n        entity.setScheduledFor(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setScheduledFor(entity.getScheduledFor());\n        entity.setGeneratedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setGeneratedAt(entity.getGeneratedAt());\n        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setExpiresAt(entity.getExpiresAt());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setUpdatedAt(entity.getUpdatedAt());\n        entity.setUser(createTestUser());\n        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(reportMapper.toEntity(any())).willReturn(entity);
            given(reportRepository.save(any())).willReturn(entity);
            given(reportMapper.toDTO(any())).willReturn(dto);

            // Act
            ReportDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());\n        assertThat(result.getReportName()).isEqualTo(dto.getReportName());\n        assertThat(result.getReportType()).isEqualTo(dto.getReportType());\n        assertThat(result.getStatus()).isEqualTo(dto.getStatus());\n        assertThat(result.getParameters()).isEqualTo(dto.getParameters());\n        assertThat(result.getFilePath()).isEqualTo(dto.getFilePath());\n        assertThat(result.getFileSize()).isEqualTo(dto.getFileSize());\n        assertThat(result.getFormat()).isEqualTo(dto.getFormat());\n        assertThat(result.getScheduledFor()).isEqualTo(dto.getScheduledFor());\n        assertThat(result.getGeneratedAt()).isEqualTo(dto.getGeneratedAt());\n        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());\n        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(reportRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(reportRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(reportMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<ReportDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(reportRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(reportRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("Report", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(reportRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(reportRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(reportRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<Report> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(reportRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(reportRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            ReportDTO partialDto = new ReportDTO();
            partialDto.setUserid(dto.getUserid());
            given(reportRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(reportMapper).should().partialUpdate(partialDto, entity);
            then(reportRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(reportRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(reportRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }