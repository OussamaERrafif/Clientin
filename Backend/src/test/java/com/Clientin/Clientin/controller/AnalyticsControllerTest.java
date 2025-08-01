package com.Clientin.Clientin.controller;

    import com.Clientin.Clientin.dto.AnalyticsDTO;
    import com.Clientin.Clientin.service.AnalyticsService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.data.domain.*;
    import org.springframework.hateoas.*;
    import org.springframework.http.*;
    import org.springframework.security.test.context.support.WithMockUser;
    import org.springframework.test.web.servlet.MockMvc;
    import java.util.*;
    import static org.mockito.BDDMockito.*;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(AnalyticsController.class)
    class AnalyticsControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private AnalyticsService analyticsService;
        @Autowired
        private ObjectMapper objectMapper;

        private final String TEST_ID = "TEST_ID_123";
        private final AnalyticsDTO validDto = createValidDto();
        private final String BASE_URL = "/api/v1/analyticss";

        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_READ")
        void getById_ShouldReturnResourceWithLinks() throws Exception {
            given(analyticsService.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_WRITE")
        void create_ShouldReturnCreatedWithLocation() throws Exception {
            given(analyticsService.create(any())).willReturn(validDto);

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
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
        @WithMockUser(authorities = "SCOPE_ANALYTICS_WRITE")
        void create_ShouldValidateInput() throws Exception {
            AnalyticsDTO invalidDto = new AnalyticsDTO();

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_READ")
        void getAll_ShouldReturnPagedResources() throws Exception {
            Page<AnalyticsDTO> page = new PageImpl<>(List.of(validDto));
            given(analyticsService.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.analyticsList[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_DELETE")
        void delete_ShouldReturnNoContent() throws Exception {
            given(analyticsService.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID)
                    .with(csrf()))
                .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_UPDATE")
        void partialUpdate_ShouldHandleInvalidData() throws Exception {
            given(analyticsService.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                    .with(csrf())
                    .content("{}"))
                .andExpect(status().isBadRequest());
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(analyticsService.getEmployee(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/employee", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private AnalyticsDTO createValidDto() {
            return AnalyticsDTO.builder()
                .id(TEST_ID)
                .metricKey("Test metricKey")
            .hourKey(42)
            .employeeId("Test employeeId")
            .department("Test department")
            .metadata("Test metadata")
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