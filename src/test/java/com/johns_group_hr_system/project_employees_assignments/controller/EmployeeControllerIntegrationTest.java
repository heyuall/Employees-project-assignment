package com.johns_group_hr_system.project_employees_assignments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testFilterEmployees() throws Exception {
        when(employeeService.filterEmployees(any(), any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employees/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void testFilterEmployeesWithPartialNameAndRole() throws Exception {
        when(employeeService.filterEmployees(any(), any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employees/list")
                        .param("partialName", "John")
                        .param("role", "DEVELOPER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Verify that the service method was called with the correct parameters
        verify(employeeService).filterEmployees("John", "DEVELOPER", null);
    }

    @Test
    public void testFilterEmployeesWithProjectId() throws Exception {
        when(employeeService.filterEmployees(any(), any(), any())).thenReturn(Collections.emptyList());

        UUID projectId = UUID.randomUUID();

        mockMvc.perform(get("/employees/list")
                        .param("projectId", projectId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Verify that the service method was called with the correct parameters
        verify(employeeService).filterEmployees(null, null, projectId);
    }

    @Test
    public void testFilterEmployeesWithInvalidRole() throws Exception {
        when(employeeService.filterEmployees(any(), any(), any()))
                .thenThrow(new IllegalArgumentException("Invalid role"));

        mockMvc.perform(get("/employees/list")
                        .param("role", "INVALID_ROLE"))
                .andExpect(status().isBadRequest());

        // Verify that the service method was called with the correct parameters
        verify(employeeService).filterEmployees(null, "INVALID_ROLE", null);
    }

    @Test
    public void testFilterEmployeesWithException() throws Exception {
        when(employeeService.filterEmployees(any(), any(), any()))
                .thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/employees/list"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        UUID employeeId = UUID.randomUUID();
        Employee mockEmployee = new Employee();
        mockEmployee.setId(employeeId);
        when(employeeService.getEmployeeById(eq(employeeId))).thenReturn(mockEmployee);

        mockMvc.perform(get("/employees/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockEmployee.getId().toString()));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        String jsonRequest = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(post("/employees/create")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEvaluateEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        Employee mockEmployee = new Employee();
        mockEmployee.setId(employeeId);
        when(employeeService.evaluateEmployee(eq(employeeId), anyInt())).thenReturn(mockEmployee);

        mockMvc.perform(post("/employees/employees/{id}/evaluate", employeeId)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .param("score", "90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockEmployee.getId().toString()));
    }
}
