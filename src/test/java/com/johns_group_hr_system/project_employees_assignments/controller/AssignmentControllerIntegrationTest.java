package com.johns_group_hr_system.project_employees_assignments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johns_group_hr_system.project_employees_assignments.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssignmentController.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
public class AssignmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testAssignProjectsToEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        String jsonRequest = objectMapper.writeValueAsString(Collections.singletonList(UUID.randomUUID()));

        mockMvc.perform(post("/assignments/assign/{employeeId}", employeeId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(employeeService).assignProjectsToEmployee(eq(employeeId), anyList());
    }

    @Test
    public void testUnassignProjectsFromEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        String jsonRequest = objectMapper.writeValueAsString(Collections.singletonList(UUID.randomUUID()));

        mockMvc.perform(post("/assignments/unassign/{employeeId}", employeeId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(employeeService).unAssignProjectsFromEmployee(eq(employeeId), anyList());
    }
}
