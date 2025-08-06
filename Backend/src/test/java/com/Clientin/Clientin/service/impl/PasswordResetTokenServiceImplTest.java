package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.PasswordResetToken;
    import com.Clientin.Clientin.dto.PasswordResetTokenDTO;
    import com.Clientin.Clientin.mapper.PasswordResetTokenMapper;
    import com.Clientin.Clientin.repository.PasswordResetTokenRepository;
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
    class PasswordResetTokenServiceImplTest {

        @Mock
        private PasswordResetTokenRepository passwordResetTokenRepository;
        @Mock
        private PasswordResetTokenMapper passwordResetTokenMapper;
        @InjectMocks
        private PasswordResetTokenServiceImpl service;

        private PasswordResetToken entity;
        private PasswordResetTokenDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<PasswordResetToken> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new PasswordResetToken();
            dto = new PasswordResetTokenDTO();
            entity.setUserId("Test Userid");
        dto.setUserId(entity.getUserId());
        entity.setTokenHash("Test Tokenhash");
        dto.setTokenHash(entity.getTokenHash());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setExpiresAt(entity.getExpiresAt());
        entity.setUsed(createTestBoolean());
        dto.setUsed(entity.getUsed());
        entity.setIpAddress("Test Ipaddress");
        dto.setIpAddress(entity.getIpAddress());
        entity.setUser(createTestUser());
        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(passwordResetTokenMapper.toEntity(any())).willReturn(entity);
            given(passwordResetTokenRepository.save(any())).willReturn(entity);
            given(passwordResetTokenMapper.toDTO(any())).willReturn(dto);

            // Act
            PasswordResetTokenDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getTokenHash()).isEqualTo(dto.getTokenHash());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());
        assertThat(result.getUsed()).isEqualTo(dto.getUsed());
        assertThat(result.getIpAddress()).isEqualTo(dto.getIpAddress());
        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(passwordResetTokenRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(passwordResetTokenRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(passwordResetTokenMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<PasswordResetTokenDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(passwordResetTokenRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(passwordResetTokenRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("PasswordResetToken", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(passwordResetTokenRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(passwordResetTokenRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(passwordResetTokenRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<PasswordResetToken> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(passwordResetTokenRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(passwordResetTokenRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            PasswordResetTokenDTO partialDto = new PasswordResetTokenDTO();
            partialDto.setUserid(dto.getUserid());
            given(passwordResetTokenRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(passwordResetTokenMapper).should().partialUpdate(partialDto, entity);
            then(passwordResetTokenRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(passwordResetTokenRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(passwordResetTokenRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
