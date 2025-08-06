package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.Notification;
    import com.Clientin.Clientin.dto.NotificationDTO;
    import com.Clientin.Clientin.mapper.NotificationMapper;
    import com.Clientin.Clientin.repository.NotificationRepository;
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
    class NotificationServiceImplTest {

        @Mock
        private NotificationRepository notificationRepository;
        @Mock
        private NotificationMapper notificationMapper;
        @InjectMocks
        private NotificationServiceImpl service;

        private Notification entity;
        private NotificationDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<Notification> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new Notification();
            dto = new NotificationDTO();
            entity.setUserId("Test Userid");
        dto.setUserId(entity.getUserId());
        entity.setTitle("Test Title");
        dto.setTitle(entity.getTitle());
        entity.setMessage("Test Message");
        dto.setMessage(entity.getMessage());
        entity.setType(createTestNotificationType());
        dto.setType(entity.getType());
        entity.setPriority(createTestPriority());
        dto.setPriority(entity.getPriority());
        entity.setReadStatus(createTestBoolean());
        dto.setReadStatus(entity.getReadStatus());
        entity.setReadAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setReadAt(entity.getReadAt());
        entity.setActionUrl("Test Actionurl");
        dto.setActionUrl(entity.getActionUrl());
        entity.setMetadata("Test Metadata");
        dto.setMetadata(entity.getMetadata());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setExpiresAt(entity.getExpiresAt());
        entity.setUser(createTestUser());
        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(notificationMapper.toEntity(any())).willReturn(entity);
            given(notificationRepository.save(any())).willReturn(entity);
            given(notificationMapper.toDTO(any())).willReturn(dto);

            // Act
            NotificationDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getMessage()).isEqualTo(dto.getMessage());
        assertThat(result.getType()).isEqualTo(dto.getType());
        assertThat(result.getPriority()).isEqualTo(dto.getPriority());
        assertThat(result.getReadStatus()).isEqualTo(dto.getReadStatus());
        assertThat(result.getReadAt()).isEqualTo(dto.getReadAt());
        assertThat(result.getActionUrl()).isEqualTo(dto.getActionUrl());
        assertThat(result.getMetadata()).isEqualTo(dto.getMetadata());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());
        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(notificationRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(notificationRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(notificationMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<NotificationDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(notificationRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(notificationRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("Notification", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(notificationRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(notificationRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(notificationRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<Notification> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(notificationRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(notificationRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            NotificationDTO partialDto = new NotificationDTO();
            partialDto.setUserid(dto.getUserid());
            given(notificationRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(notificationMapper).should().partialUpdate(partialDto, entity);
            then(notificationRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(notificationRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(notificationRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
