package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.NFCDevice;
    import com.Clientin.Clientin.dto.NFCDeviceDTO;
    import com.Clientin.Clientin.mapper.NFCDeviceMapper;
    import com.Clientin.Clientin.repository.NFCDeviceRepository;
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
    class NFCDeviceServiceImplTest {

        @Mock
        private NFCDeviceRepository nFCDeviceRepository;
        @Mock
        private NFCDeviceMapper nFCDeviceMapper;
        @InjectMocks
        private NFCDeviceServiceImpl service;

        private NFCDevice entity;
        private NFCDeviceDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<NFCDevice> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new NFCDevice();
            dto = new NFCDeviceDTO();
            entity.setDeviceName("Test Devicename");\n        dto.setDeviceName(entity.getDeviceName());\n        entity.setDeviceSerial("Test Deviceserial");\n        dto.setDeviceSerial(entity.getDeviceSerial());\n        entity.setLocation("Test Location");\n        dto.setLocation(entity.getLocation());\n        entity.setStatus(createTestDeviceStatus());\n        dto.setStatus(entity.getStatus());\n        entity.setFirmwareVersion("Test Firmwareversion");\n        dto.setFirmwareVersion(entity.getFirmwareVersion());\n        entity.setBatteryLevel(42);\n        dto.setBatteryLevel(entity.getBatteryLevel());\n        entity.setLastPing(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setLastPing(entity.getLastPing());\n        entity.setConfiguration("Test Configuration");\n        dto.setConfiguration(entity.getConfiguration());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setUpdatedAt(entity.getUpdatedAt());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(nFCDeviceMapper.toEntity(any())).willReturn(entity);
            given(nFCDeviceRepository.save(any())).willReturn(entity);
            given(nFCDeviceMapper.toDTO(any())).willReturn(dto);

            // Act
            NFCDeviceDTO result = service.create(dto);

            // Assert
            assertThat(result.getDeviceName()).isEqualTo(dto.getDeviceName());\n        assertThat(result.getDeviceSerial()).isEqualTo(dto.getDeviceSerial());\n        assertThat(result.getLocation()).isEqualTo(dto.getLocation());\n        assertThat(result.getStatus()).isEqualTo(dto.getStatus());\n        assertThat(result.getFirmwareVersion()).isEqualTo(dto.getFirmwareVersion());\n        assertThat(result.getBatteryLevel()).isEqualTo(dto.getBatteryLevel());\n        assertThat(result.getLastPing()).isEqualTo(dto.getLastPing());\n        assertThat(result.getConfiguration()).isEqualTo(dto.getConfiguration());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
            then(nFCDeviceRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(nFCDeviceRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(nFCDeviceMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<NFCDeviceDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(nFCDeviceRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(nFCDeviceRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("NFCDevice", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(nFCDeviceRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(nFCDeviceRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(nFCDeviceRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<NFCDevice> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(nFCDeviceRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(nFCDeviceRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            NFCDeviceDTO partialDto = new NFCDeviceDTO();
            partialDto.setDevicename(dto.getDevicename());
            given(nFCDeviceRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(nFCDeviceMapper).should().partialUpdate(partialDto, entity);
            then(nFCDeviceRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(nFCDeviceRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(nFCDeviceRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }