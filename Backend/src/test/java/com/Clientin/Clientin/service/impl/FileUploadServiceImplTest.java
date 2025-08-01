package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.FileUpload;
    import com.Clientin.Clientin.dto.FileUploadDTO;
    import com.Clientin.Clientin.mapper.FileUploadMapper;
    import com.Clientin.Clientin.repository.FileUploadRepository;
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
    class FileUploadServiceImplTest {

        @Mock
        private FileUploadRepository fileUploadRepository;
        @Mock
        private FileUploadMapper fileUploadMapper;
        @InjectMocks
        private FileUploadServiceImpl service;

        private FileUpload entity;
        private FileUploadDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<FileUpload> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new FileUpload();
            dto = new FileUploadDTO();
            entity.setUserId("Test Userid");\n        dto.setUserId(entity.getUserId());\n        entity.setOriginalFilename("Test Originalfilename");\n        dto.setOriginalFilename(entity.getOriginalFilename());\n        entity.setStoredFilename("Test Storedfilename");\n        dto.setStoredFilename(entity.getStoredFilename());\n        entity.setFilePath("Test Filepath");\n        dto.setFilePath(entity.getFilePath());\n        entity.setContentType("Test Contenttype");\n        dto.setContentType(entity.getContentType());\n        entity.setFileSize(createTestLong());\n        dto.setFileSize(entity.getFileSize());\n        entity.setFileType(createTestFileType());\n        dto.setFileType(entity.getFileType());\n        entity.setEntityType("Test Entitytype");\n        dto.setEntityType(entity.getEntityType());\n        entity.setEntityId("Test Entityid");\n        dto.setEntityId(entity.getEntityId());\n        entity.setFileHash("Test Filehash");\n        dto.setFileHash(entity.getFileHash());\n        entity.setIsPublic(createTestBoolean());\n        dto.setIsPublic(entity.getIsPublic());\n        entity.setDownloadCount(createTestLong());\n        dto.setDownloadCount(entity.getDownloadCount());\n        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setCreatedAt(entity.getCreatedAt());\n        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));\n        dto.setExpiresAt(entity.getExpiresAt());\n        entity.setUser(createTestUser());\n        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(fileUploadMapper.toEntity(any())).willReturn(entity);
            given(fileUploadRepository.save(any())).willReturn(entity);
            given(fileUploadMapper.toDTO(any())).willReturn(dto);

            // Act
            FileUploadDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());\n        assertThat(result.getOriginalFilename()).isEqualTo(dto.getOriginalFilename());\n        assertThat(result.getStoredFilename()).isEqualTo(dto.getStoredFilename());\n        assertThat(result.getFilePath()).isEqualTo(dto.getFilePath());\n        assertThat(result.getContentType()).isEqualTo(dto.getContentType());\n        assertThat(result.getFileSize()).isEqualTo(dto.getFileSize());\n        assertThat(result.getFileType()).isEqualTo(dto.getFileType());\n        assertThat(result.getEntityType()).isEqualTo(dto.getEntityType());\n        assertThat(result.getEntityId()).isEqualTo(dto.getEntityId());\n        assertThat(result.getFileHash()).isEqualTo(dto.getFileHash());\n        assertThat(result.getIsPublic()).isEqualTo(dto.getIsPublic());\n        assertThat(result.getDownloadCount()).isEqualTo(dto.getDownloadCount());\n        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());\n        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());\n        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(fileUploadRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(fileUploadRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(fileUploadMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<FileUploadDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(fileUploadRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(fileUploadRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("FileUpload", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(fileUploadRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(fileUploadRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(fileUploadRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<FileUpload> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(fileUploadRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(fileUploadRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            FileUploadDTO partialDto = new FileUploadDTO();
            partialDto.setUserid(dto.getUserid());
            given(fileUploadRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(fileUploadMapper).should().partialUpdate(partialDto, entity);
            then(fileUploadRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(fileUploadRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(fileUploadRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }