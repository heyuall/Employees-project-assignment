package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.repository.IEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private IEmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(IEmployeeRepository.class);
        modelMapper = new ModelMapper();
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void should_create_employee_with_valid_input_and_save_employee() {
        // given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("John Doe");
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setRole("DEVELOPER");

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        when(employeeRepository.save(any())).thenReturn(employee);

        // when
        employeeService.createEmployee(employeeDto);

        // then
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void should_get_employee_by_valid_id_and_return_employee() {
        // given
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setId(id);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // when
        Employee result = employeeService.getEmployeeById(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void should_return_null_for_invalid_employee_id() {
        // given
        UUID id = UUID.randomUUID();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Employee result = employeeService.getEmployeeById(id);

        // then
        assertNull(result);
    }

    @Test
    void should_return_list_of_all_employees() {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        // when
        List<Employee> result = employeeService.getAllEmployees();

        // then
        assertEquals(employees.size(), result.size());
    }

    @Test
    void should_return_list_of_filtered_employees_with_valid_input() {
        // given
        String partialName = "John";
        String role = "DEVELOPER";
        UUID projectId = UUID.randomUUID();

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeRepository.findByNameContainingIgnoreCaseAndRoleIgnoreCaseAndProjectsId(
                "%" + partialName + "%", role, projectId))
                .thenReturn(employees);

        // when
        List<Employee> result = employeeService.filterEmployees(partialName, role, projectId);

        // then
        assertEquals(employees.size(), result.size());
    }

    @Test
    void should_update_and_return_employee_with_valid_input_for_evaluation() {
        // given
        UUID id = UUID.randomUUID();
        int score = 80;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setScore(50);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        // when
        Employee result = employeeService.evaluateEmployee(id, score);

        // then
        assertNotNull(result);
        assertEquals(score, result.getScore());
    }

    @Test
    void should_throw_null_pointer_exception_for_invalid_employee_id() {
        // given
        UUID id = UUID.randomUUID();
        int score = 80;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NullPointerException.class, () -> employeeService.evaluateEmployee(id, score));
    }

    @Test
    void should_throw_illegal_argument_exception_for_invalid_score() {
        // given
        UUID id = UUID.randomUUID();
        int invalidScore = -10;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> employeeService.evaluateEmployee(id, invalidScore));
    }
}
