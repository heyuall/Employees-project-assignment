package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.repository.IEmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService{

    private final IEmployeeRepository employeeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(UUID id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> filterEmployees(String partialName, String role, UUID projectId) {
        if (partialName != null && !partialName.isEmpty()) {
            partialName = "%" + partialName + "%";
        }

        return employeeRepository.findByNameContainingIgnoreCaseAndRoleIgnoreCaseAndProjectsId(partialName, role, projectId);
    }

    @Override
    public Employee evaluateEmployee(UUID id, int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Employee with ID " + id + " not found."));

        employee.setScore(score);
        return employeeRepository.save(employee);
    }
}

