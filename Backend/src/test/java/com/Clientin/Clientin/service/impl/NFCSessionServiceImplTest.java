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
            entity.setDeviceId("Test Deviceid");\n        dto.setDeviceId(entity.getDeviceId());\n        entity.setSessionToken("Test Sessiontoken");\n        dto.setSessionToken(entity.getSessionToken());\n        entity.setClientId("Test Clientid");\n        dto.setClientId(entity.getClientId());\n        entity.setStatus(createTestSessionStatus());\n        dto.setStatus(entity.getStatus());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setExpiresAt(entity.getExpiresAt());\n        entity.setCompletedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCompletedAt(entity.getCompletedAt());\n        entity.setMetadata("Test Metadata");\n        dto.setMetadata(entity.getMetadata());\n        entity.setDevice(createTestNFCDevice());\n        dto.setDevice(entity.getDevice());\n        entity.setClient(createTestClient());\n        dto.setClient(entity.getClient());
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
            assertThat(result.getDeviceId()).isEqualTo(dto.getDeviceId());\n        assertThat(result.getSessionToken()).isEqualTo(dto.getSessionToken());\n        assertThat(result.getClientId()).isEqualTo(dto.getClientId());\n        assertThat(result.getStatus()).isEqualTo(dto.getStatus());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());\n        assertThat(result.getCompletedAt()).isEqualTo(dto.getCompletedAt());\n        assertThat(result.getMetadata()).isEqualTo(dto.getMetadata());\n        assertThat(result.getDevice()).isEqualTo(dto.getDevice());\n        assertThat(result.getClient()).isEqualTo(dto.getClient());
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