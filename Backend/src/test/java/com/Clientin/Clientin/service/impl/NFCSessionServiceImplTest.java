package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.NFCSession;
    import com.Clientin.Clientin.dto.NFCSessionDTO;
    import com.Clientin.Clientin.mapper.NFCSessionMapper;
    import com.Clientin.Clientin.repository.NFCSessionRepository;
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
    class NFCSessionServiceImplTest {

        @Mock
        private NFCSessionRepository nFCSessionRepository;
        @Mock
        private NFCSessionMapper nFCSessionMapper;
        @InjectMocks
        private NFCSessionServiceImpl service;

        private NFCSession entity;
        private NFCSessionDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<NFCSession> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new NFCSession();
            dto = new NFCSessionDTO();
            entity.setDeviceId("Test Deviceid");
        dto.setDeviceId(entity.getDeviceId());
        entity.setSessionToken("Test Sessiontoken");
        dto.setSessionToken(entity.getSessionToken());
        entity.setClientId("Test Clientid");
        dto.setClientId(entity.getClientId());
        entity.setStatus(createTestSessionStatus());
        dto.setStatus(entity.getStatus());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setExpiresAt(entity.getExpiresAt());
        entity.setCompletedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCompletedAt(entity.getCompletedAt());
        entity.setMetadata("Test Metadata");
        dto.setMetadata(entity.getMetadata());
        entity.setDevice(createTestNFCDevice());
        dto.setDevice(entity.getDevice());
        entity.setClient(createTestClient());
        dto.setClient(entity.getClient());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(nFCSessionMapper.toEntity(any())).willReturn(entity);
            given(nFCSessionRepository.save(any())).willReturn(entity);
            given(nFCSessionMapper.toDTO(any())).willReturn(dto);

            // Act
            NFCSessionDTO result = service.create(dto);

            // Assert
            assertThat(result.getDeviceId()).isEqualTo(dto.getDeviceId());
        assertThat(result.getSessionToken()).isEqualTo(dto.getSessionToken());
        assertThat(result.getClientId()).isEqualTo(dto.getClientId());
        assertThat(result.getStatus()).isEqualTo(dto.getStatus());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());
        assertThat(result.getCompletedAt()).isEqualTo(dto.getCompletedAt());
        assertThat(result.getMetadata()).isEqualTo(dto.getMetadata());
        assertThat(result.getDevice()).isEqualTo(dto.getDevice());
        assertThat(result.getClient()).isEqualTo(dto.getClient());
            then(nFCSessionRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(nFCSessionRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(nFCSessionMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<NFCSessionDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(nFCSessionRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(nFCSessionRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("NFCSession", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(nFCSessionRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(nFCSessionRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(nFCSessionRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<NFCSession> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(nFCSessionRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(nFCSessionRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            NFCSessionDTO partialDto = new NFCSessionDTO();
            partialDto.setDeviceid(dto.getDeviceid());
            given(nFCSessionRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(nFCSessionMapper).should().partialUpdate(partialDto, entity);
            then(nFCSessionRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(nFCSessionRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(nFCSessionRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
