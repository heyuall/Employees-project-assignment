package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.entity.Project;
import com.johns_group_hr_system.project_employees_assignments.entity.enums.EmployeeRoleEnum;
import com.johns_group_hr_system.project_employees_assignments.repository.IEmployeeRepository;
import com.johns_group_hr_system.project_employees_assignments.repository.IProjectRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService{

    private final IEmployeeRepository employeeRepository;

    private final IProjectRepository projectRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, IProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
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
    public List<Employee> filterEmployees(String partialName, String role, UUID projectId) throws IllegalArgumentException {
        if (partialName != null && !partialName.isEmpty()) {
            partialName = "%" + partialName + "%";
        }

        try {
            EmployeeRoleEnum roleEnum = EmployeeRoleEnum.valueOf(role);
            return employeeRepository.findByNameContainingIgnoreCaseAndRoleAndProjectsContains(partialName, roleEnum, projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
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

    @Transactional
    @Override
    public void assignProjectsToEmployee(UUID employeeId, List<UUID> projectIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        List<Project> projects = projectRepository.findAllById(projectIds);
        employee.getProjects().addAll(projects);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void unAssignProjectsFromEmployee(UUID employeeId, List<UUID> projectIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        List<Project> projects = projectRepository.findAllById(projectIds);
        employee.getProjects().removeAll(projects);

        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return null;
    }
}

