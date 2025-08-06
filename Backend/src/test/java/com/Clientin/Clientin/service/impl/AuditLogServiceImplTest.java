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
            entity.setUserId("Test Userid");
        dto.setUserId(entity.getUserId());
        entity.setEntityType("Test Entitytype");
        dto.setEntityType(entity.getEntityType());
        entity.setEntityId("Test Entityid");
        dto.setEntityId(entity.getEntityId());
        entity.setAction(createTestAction());
        dto.setAction(entity.getAction());
        entity.setOldValues("Test Oldvalues");
        dto.setOldValues(entity.getOldValues());
        entity.setNewValues("Test Newvalues");
        dto.setNewValues(entity.getNewValues());
        entity.setIpAddress("Test Ipaddress");
        dto.setIpAddress(entity.getIpAddress());
        entity.setUserAgent("Test Useragent");
        dto.setUserAgent(entity.getUserAgent());
        entity.setSessionId("Test Sessionid");
        dto.setSessionId(entity.getSessionId());
        entity.setRequestId("Test Requestid");
        dto.setRequestId(entity.getRequestId());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setUser(createTestUser());
        dto.setUser(entity.getUser());
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
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getEntityType()).isEqualTo(dto.getEntityType());
        assertThat(result.getEntityId()).isEqualTo(dto.getEntityId());
        assertThat(result.getAction()).isEqualTo(dto.getAction());
        assertThat(result.getOldValues()).isEqualTo(dto.getOldValues());
        assertThat(result.getNewValues()).isEqualTo(dto.getNewValues());
        assertThat(result.getIpAddress()).isEqualTo(dto.getIpAddress());
        assertThat(result.getUserAgent()).isEqualTo(dto.getUserAgent());
        assertThat(result.getSessionId()).isEqualTo(dto.getSessionId());
        assertThat(result.getRequestId()).isEqualTo(dto.getRequestId());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getUser()).isEqualTo(dto.getUser());
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
