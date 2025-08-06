package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.AuthToken;
    import com.Clientin.Clientin.dto.AuthTokenDTO;
    import com.Clientin.Clientin.mapper.AuthTokenMapper;
    import com.Clientin.Clientin.repository.AuthTokenRepository;
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
    class AuthTokenServiceImplTest {

        @Mock
        private AuthTokenRepository authTokenRepository;
        @Mock
        private AuthTokenMapper authTokenMapper;
        @InjectMocks
        private AuthTokenServiceImpl service;

        private AuthToken entity;
        private AuthTokenDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<AuthToken> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new AuthToken();
            dto = new AuthTokenDTO();
            entity.setUserId("Test Userid");
        dto.setUserId(entity.getUserId());
        entity.setTokenHash("Test Tokenhash");
        dto.setTokenHash(entity.getTokenHash());
        entity.setTokenType(createTestTokenType());
        dto.setTokenType(entity.getTokenType());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setExpiresAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setExpiresAt(entity.getExpiresAt());
        entity.setRevoked(createTestBoolean());
        dto.setRevoked(entity.getRevoked());
        entity.setDeviceInfo("Test Deviceinfo");
        dto.setDeviceInfo(entity.getDeviceInfo());
        entity.setIpAddress("Test Ipaddress");
        dto.setIpAddress(entity.getIpAddress());
        entity.setUser(createTestUser());
        dto.setUser(entity.getUser());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(authTokenMapper.toEntity(any())).willReturn(entity);
            given(authTokenRepository.save(any())).willReturn(entity);
            given(authTokenMapper.toDTO(any())).willReturn(dto);

            // Act
            AuthTokenDTO result = service.create(dto);

            // Assert
            assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getTokenHash()).isEqualTo(dto.getTokenHash());
        assertThat(result.getTokenType()).isEqualTo(dto.getTokenType());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getExpiresAt()).isEqualTo(dto.getExpiresAt());
        assertThat(result.getRevoked()).isEqualTo(dto.getRevoked());
        assertThat(result.getDeviceInfo()).isEqualTo(dto.getDeviceInfo());
        assertThat(result.getIpAddress()).isEqualTo(dto.getIpAddress());
        assertThat(result.getUser()).isEqualTo(dto.getUser());
            then(authTokenRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(authTokenRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(authTokenMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<AuthTokenDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(authTokenRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(authTokenRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("AuthToken", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(authTokenRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(authTokenRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(authTokenRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<AuthToken> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(authTokenRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(authTokenRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            AuthTokenDTO partialDto = new AuthTokenDTO();
            partialDto.setUserid(dto.getUserid());
            given(authTokenRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(authTokenMapper).should().partialUpdate(partialDto, entity);
            then(authTokenRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(authTokenRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(authTokenRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
