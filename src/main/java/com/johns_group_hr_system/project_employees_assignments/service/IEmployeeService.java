package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {
    void createEmployee(EmployeeDto employeeDto);

    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();

    List<Employee> filterEmployees(String partialName, String role, UUID projectId);
}
