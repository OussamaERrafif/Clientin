package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.AuditLog;
    import com.Clientin.Clientin.dto.AuditLogDTO;
    import com.Clientin.Clientin.mapper.AuditLogMapper;
    import com.Clientin.Clientin.repository.AuditLogRepository;
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
    class AuditLogServiceImplTest {

        @Mock
        private AuditLogRepository auditLogRepository;
        @Mock
        private AuditLogMapper auditLogMapper;
        @InjectMocks
        private AuditLogServiceImpl service;

        private AuditLog entity;
        private AuditLogDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<AuditLog> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new AuditLog();
            dto = new AuditLogDTO();
            entity.setUserId("Test Userid");\n        dto.setUserId(entity.getUserId());\n        entity.setEntityType("Test Entitytype");\n        dto.setEntityType(entity.getEntityType());\n        entity.setEntityId("Test Entityid");\n        dto.setEntityId(entity.getEntityId());\n        entity.setAction(createTestAction());\n        dto.setAction(entity.getAction());\n        entity.setOldValues("Test Oldvalues");\n        dto.setOldValues(entity.getOldValues());\n        entity.setNewValues("Test Newvalues");\n        dto.setNewValues(entity.getNewValues());\n        entity.setIpAddress("Test Ipaddress");\n        dto.setIpAddress(entity.getIpAddress());\n        entity.setUserAgent("Test Useragent");\n        dto.setUserAgent(entity.getUserAgent());\n        entity.setSessionId("Test Sessionid");\n        dto.setSessionId(entity.getSessionId());\n        entity.setRequestId("Test Requestid");\n        dto.setRequestId(entity.getRequestId());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setUser(createTestUser());\n        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(auditLogMapper.toEntity(any())).willReturn(entity);
            given(auditLogRepository.save(any())).willReturn(entity);
            given(auditLogMapper.toDTO(any())).willReturn(dto);

            // Act
            AuditLogDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());\n        assertThat(result.getEntityType()).isEqualTo(dto.getEntityType());\n        assertThat(result.getEntityId()).isEqualTo(dto.getEntityId());\n        assertThat(result.getAction()).isEqualTo(dto.getAction());\n        assertThat(result.getOldValues()).isEqualTo(dto.getOldValues());\n        assertThat(result.getNewValues()).isEqualTo(dto.getNewValues());\n        assertThat(result.getIpAddress()).isEqualTo(dto.getIpAddress());\n        assertThat(result.getUserAgent()).isEqualTo(dto.getUserAgent());\n        assertThat(result.getSessionId()).isEqualTo(dto.getSessionId());\n        assertThat(result.getRequestId()).isEqualTo(dto.getRequestId());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(auditLogRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(auditLogRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(auditLogMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<AuditLogDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(auditLogRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(auditLogRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("AuditLog", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(auditLogRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(auditLogRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(auditLogRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<AuditLog> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(auditLogRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(auditLogRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            AuditLogDTO partialDto = new AuditLogDTO();
            partialDto.setUserid(dto.getUserid());
            given(auditLogRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(auditLogMapper).should().partialUpdate(partialDto, entity);
            then(auditLogRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(auditLogRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(auditLogRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }