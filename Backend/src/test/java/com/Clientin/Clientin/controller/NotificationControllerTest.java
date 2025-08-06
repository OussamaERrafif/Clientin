package com.Clientin.Clientin.controller;

    import com.Clientin.Clientin.dto.NotificationDTO;
    import com.Clientin.Clientin.service.NotificationService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.data.domain.*;
    import org.springframework.hateoas.*;
    import org.springframework.http.*;
        import org.springframework.test.web.servlet.MockMvc;
    import java.util.*;
    import static org.mockito.BDDMockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(NotificationController.class)
    class NotificationControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private NotificationService notificationService;
        @Autowired
        private ObjectMapper objectMapper;

        private final String TEST_ID = "TEST_ID_123";
        private final NotificationDTO validDto = createValidDto();
        private final String BASE_URL = "/api/v1/notifications";

        @Test
        void getById_ShouldReturnResourceWithLinks() throws Exception {
            given(notificationService.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }

        @Test
        void create_ShouldReturnCreatedWithLocation() throws Exception {
            given(notificationService.create(any())).willReturn(validDto);

            mockMvc.perform(post(BASE_URL)
                    
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        void create_ShouldRequireAuthentication() throws Exception {
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                .andExpect(status().isUnauthorized());
        }

        @Test
        void create_ShouldValidateInput() throws Exception {
            NotificationDTO invalidDto = new NotificationDTO();

            mockMvc.perform(post(BASE_URL)
                    
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }

        @Test
        void getAll_ShouldReturnPagedResources() throws Exception {
            Page<NotificationDTO> page = new PageImpl<>(List.of(validDto));
            given(notificationService.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.notificationList[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        void delete_ShouldReturnNoContent() throws Exception {
            given(notificationService.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID)
                    )
                .andExpect(status().isNoContent());
        }

        @Test
        void partialUpdate_ShouldHandleInvalidData() throws Exception {
            given(notificationService.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                    
                    .content("{}"))
                .andExpect(status().isBadRequest());
        }

        @Test
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(notificationService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private NotificationDTO createValidDto() {
            return NotificationDTO.builder()
                .id(TEST_ID)
                .userId("Test userId")
            .title("Test title")
            .message("Test message")
            .readAt("2024-01-01T12:00:00")
            .actionUrl("Test actionUrl")
            .metadata("Test metadata")
            .createdAt("2024-01-01T12:00:00")
            .expiresAt("2024-01-01T12:00:00")
                .build();
        }

        // Error response structure
        static class ErrorResponse {
            public int status;
            public String message;
            public List<ValidationError> errors;
        }

        static class ValidationError {
            public String field;
            public String message;
        }
    }
