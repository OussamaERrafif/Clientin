package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.AbsenceDTO;
import com.Clientin.Clientin.entity.Absence;
import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.repository.AbsenceRepository;
import com.Clientin.Clientin.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
@Rollback
class AbsenceControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private User testEmployee;
    private User testManager;
    private AbsenceDTO testAbsenceDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                
                .build();

        // Create test users
        testEmployee = User.builder()
                .id("emp123")
                .name("John Doe")
                .email("john.doe@example.com")
                .role(User.Role.EMPLOYEE)
                .status(User.Status.ACTIVE)
                .passwordHash("hashedpassword")
                .build();

        testManager = User.builder()
                .id("mgr123")
                .name("Jane Manager")
                .email("jane.manager@example.com")
                .role(User.Role.MANAGER)
                .status(User.Status.ACTIVE)
                .passwordHash("hashedpassword")
                .build();

        userRepository.save(testEmployee);
        userRepository.save(testManager);

        // Create test absence DTO
        testAbsenceDTO = AbsenceDTO.builder()
                .employeeId("emp123")
                .absenceType(AbsenceDTO.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .isPaid(true)
                .build();
    }

    @Test
    void createAbsence_ShouldReturnCreated_WhenValidData() throws Exception {
        mockMvc.perform(post("/api/v1/absences")
                        
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAbsenceDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId", is("emp123")))
                .andExpect(jsonPath("$.absenceType", is("VACATION")))
                .andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(jsonPath("$.daysCount", is(4)))
                .andExpect(jsonPath("$.createdBy").exists())
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void createAbsence_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        testAbsenceDTO.setEmployeeId(""); // Invalid empty employee ID
        testAbsenceDTO.setStartDate(null); // Invalid null start date

        mockMvc.perform(post("/api/v1/absences")
                        
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAbsenceDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAbsence_ShouldReturnBadRequest_WhenEndDateBeforeStartDate() throws Exception {
        testAbsenceDTO.setStartDate(LocalDate.now().plusDays(10));
        testAbsenceDTO.setEndDate(LocalDate.now().plusDays(7)); // End before start

        mockMvc.perform(post("/api/v1/absences")
                        
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAbsenceDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAbsence_ShouldReturnAbsence_WhenExists() throws Exception {
        // Create test absence in database
        Absence absence = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .status(Absence.AbsenceStatus.PENDING)
                .isPaid(true)
                .build();
        
        Absence savedAbsence = absenceRepository.save(absence);

        mockMvc.perform(get("/api/v1/absences/{id}", savedAbsence.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedAbsence.getId())))
                .andExpected(jsonPath("$.absenceType", is("VACATION")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void getAbsence_ShouldReturnNotFound_WhenNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/absences/{id}", "nonexistent-id"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void approveAbsence_ShouldReturnOk_WhenValidRequest() throws Exception {
        // Create and save a pending absence
        Absence absence = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .status(Absence.AbsenceStatus.PENDING)
                .isPaid(true)
                .build();
        
        Absence savedAbsence = absenceRepository.save(absence);

        mockMvc.perform(post("/api/v1/absences/{id}/approve", savedAbsence.getId())
                        
                        .param("approverId", testManager.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("APPROVED")))
                .andExpect(jsonPath("$.approvedBy", is(testManager.getId())))
                .andExpect(jsonPath("$.approvedAt").exists());
    }

    @Test
    void rejectAbsence_ShouldReturnOk_WhenValidRequest() throws Exception {
        // Create and save a pending absence
        Absence absence = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .reason("Family vacation")
                .status(Absence.AbsenceStatus.PENDING)
                .isPaid(true)
                .build();
        
        Absence savedAbsence = absenceRepository.save(absence);

        mockMvc.perform(post("/api/v1/absences/{id}/reject", savedAbsence.getId())
                        
                        .param("approverId", testManager.getId())
                        .param("reason", "Business needs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpected(jsonPath("$.status", is("REJECTED")))
                .andExpect(jsonPath("$.approvedBy", is(testManager.getId())))
                .andExpect(jsonPath("$.comments", is("Business needs")));
    }

    @Test
    void getAbsencesByEmployee_ShouldReturnList_WhenEmployeeHasAbsences() throws Exception {
        // Create multiple absences for the employee
        Absence absence1 = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .status(Absence.AbsenceStatus.PENDING)
                .build();

        Absence absence2 = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.SICK_LEAVE)
                .startDate(LocalDate.now().plusDays(20))
                .endDate(LocalDate.now().plusDays(22))
                .daysCount(3)
                .status(Absence.AbsenceStatus.APPROVED)
                .build();

        absenceRepository.save(absence1);
        absenceRepository.save(absence2);

        mockMvc.perform(get("/api/v1/absences/employee/{employeeId}", testEmployee.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].employeeId", everyItem(is(testEmployee.getId()))));
    }

    @Test
    void getPendingApprovals_ShouldReturnPendingAbsences() throws Exception {
        // Create a mix of pending and approved absences
        Absence pendingAbsence = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .status(Absence.AbsenceStatus.PENDING)
                .build();

        Absence approvedAbsence = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.SICK_LEAVE)
                .startDate(LocalDate.now().plusDays(20))
                .endDate(LocalDate.now().plusDays(22))
                .daysCount(3)
                .status(Absence.AbsenceStatus.APPROVED)
                .build();

        absenceRepository.save(pendingAbsence);
        absenceRepository.save(approvedAbsence);

        mockMvc.perform(get("/api/v1/absences/pending"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("PENDING")));
    }

    @Test
    void getAbsencesInDateRange_ShouldReturnFilteredResults() throws Exception {
        LocalDate rangeStart = LocalDate.now().plusDays(5);
        LocalDate rangeEnd = LocalDate.now().plusDays(15);

        // Create absences - one within range, one outside
        Absence withinRange = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.VACATION)
                .startDate(LocalDate.now().plusDays(7))
                .endDate(LocalDate.now().plusDays(10))
                .daysCount(4)
                .status(Absence.AbsenceStatus.PENDING)
                .build();

        Absence outsideRange = Absence.builder()
                .employee(testEmployee)
                .absenceType(Absence.AbsenceType.SICK_LEAVE)
                .startDate(LocalDate.now().plusDays(20))
                .endDate(LocalDate.now().plusDays(22))
                .daysCount(3)
                .status(Absence.AbsenceStatus.APPROVED)
                .build();

        absenceRepository.save(withinRange);
        absenceRepository.save(outsideRange);

        mockMvc.perform(get("/api/v1/absences/date-range")
                        .param("startDate", rangeStart.toString())
                        .param("endDate", rangeEnd.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].absenceType", is("VACATION")));
    }

    @Test
    void createAbsence_ShouldReturnUnauthorized_WhenNotAuthenticated() throws Exception {
        mockMvc.perform(post("/api/v1/absences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAbsenceDTO)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createAbsence_ShouldReturnForbidden_WhenInsufficientAuthority() throws Exception {
        mockMvc.perform(post("/api/v1/absences")
                        
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAbsenceDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
