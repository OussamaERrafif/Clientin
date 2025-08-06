package com.Clientin.Clientin.controller;

    import com.Clientin.Clientin.dto.AuditLogDTO;
    import com.Clientin.Clientin.service.AuditLogService;
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

    @WebMvcTest(AuditLogController.class)
    class AuditLogControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private AuditLogService auditLogService;
        @Autowired
        private ObjectMapper objectMapper;

        private final String TEST_ID = "TEST_ID_123";
        private final AuditLogDTO validDto = createValidDto();
        private final String BASE_URL = "/api/v1/auditLogs";

        @Test
        void getById_ShouldReturnResourceWithLinks() throws Exception {
            given(auditLogService.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }

        @Test
        void create_ShouldReturnCreatedWithLocation() throws Exception {
            given(auditLogService.create(any())).willReturn(validDto);

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
            AuditLogDTO invalidDto = new AuditLogDTO();

            mockMvc.perform(post(BASE_URL)
                    
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }

        @Test
        void getAll_ShouldReturnPagedResources() throws Exception {
            Page<AuditLogDTO> page = new PageImpl<>(List.of(validDto));
            given(auditLogService.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.auditLogList[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        void delete_ShouldReturnNoContent() throws Exception {
            given(auditLogService.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID)
                    )
                .andExpect(status().isNoContent());
        }

        @Test
        void partialUpdate_ShouldHandleInvalidData() throws Exception {
            given(auditLogService.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                    
                    .content("{}"))
                .andExpect(status().isBadRequest());
        }

        @Test
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(auditLogService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private AuditLogDTO createValidDto() {
            return AuditLogDTO.builder()
                .id(TEST_ID)
                .userId("Test userId")
            .entityType("Test entityType")
            .entityId("Test entityId")
            .oldValues("Test oldValues")
            .newValues("Test newValues")
            .ipAddress("Test ipAddress")
            .userAgent("Test userAgent")
            .sessionId("Test sessionId")
            .requestId("Test requestId")
            .createdAt("2024-01-01T12:00:00")
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
