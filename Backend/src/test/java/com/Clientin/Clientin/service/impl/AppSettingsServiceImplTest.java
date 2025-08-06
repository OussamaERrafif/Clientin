package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.AppSettings;
    import com.Clientin.Clientin.dto.AppSettingsDTO;
    import com.Clientin.Clientin.mapper.AppSettingsMapper;
    import com.Clientin.Clientin.repository.AppSettingsRepository;
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
    class AppSettingsServiceImplTest {

        @Mock
        private AppSettingsRepository appSettingsRepository;
        @Mock
        private AppSettingsMapper appSettingsMapper;
        @InjectMocks
        private AppSettingsServiceImpl service;

        private AppSettings entity;
        private AppSettingsDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<AppSettings> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new AppSettings();
            dto = new AppSettingsDTO();
            entity.setSettingKey("Test Settingkey");
        dto.setSettingKey(entity.getSettingKey());
        entity.setSettingValue("Test Settingvalue");
        dto.setSettingValue(entity.getSettingValue());
        entity.setSettingType(createTestSettingType());
        dto.setSettingType(entity.getSettingType());
        entity.setCategory("Test Category");
        dto.setCategory(entity.getCategory());
        entity.setDescription("Test Description");
        dto.setDescription(entity.getDescription());
        entity.setDefaultValue("Test Defaultvalue");
        dto.setDefaultValue(entity.getDefaultValue());
        entity.setValidationRules("Test Validationrules");
        dto.setValidationRules(entity.getValidationRules());
        entity.setIsPublic(createTestBoolean());
        dto.setIsPublic(entity.getIsPublic());
        entity.setIsEncrypted(createTestBoolean());
        dto.setIsEncrypted(entity.getIsEncrypted());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setUpdatedAt(entity.getUpdatedAt());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(appSettingsMapper.toEntity(any())).willReturn(entity);
            given(appSettingsRepository.save(any())).willReturn(entity);
            given(appSettingsMapper.toDTO(any())).willReturn(dto);

            // Act
            AppSettingsDTO result = service.create(dto);

            // Assert
            assertThat(result.getSettingKey()).isEqualTo(dto.getSettingKey());
        assertThat(result.getSettingValue()).isEqualTo(dto.getSettingValue());
        assertThat(result.getSettingType()).isEqualTo(dto.getSettingType());
        assertThat(result.getCategory()).isEqualTo(dto.getCategory());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getDefaultValue()).isEqualTo(dto.getDefaultValue());
        assertThat(result.getValidationRules()).isEqualTo(dto.getValidationRules());
        assertThat(result.getIsPublic()).isEqualTo(dto.getIsPublic());
        assertThat(result.getIsEncrypted()).isEqualTo(dto.getIsEncrypted());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
            then(appSettingsRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(appSettingsRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(appSettingsMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<AppSettingsDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(appSettingsRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(appSettingsRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("AppSettings", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(appSettingsRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(appSettingsRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(appSettingsRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<AppSettings> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(appSettingsRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(appSettingsRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            AppSettingsDTO partialDto = new AppSettingsDTO();
            partialDto.setSettingkey(dto.getSettingkey());
            given(appSettingsRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(appSettingsMapper).should().partialUpdate(partialDto, entity);
            then(appSettingsRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(appSettingsRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(appSettingsRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
