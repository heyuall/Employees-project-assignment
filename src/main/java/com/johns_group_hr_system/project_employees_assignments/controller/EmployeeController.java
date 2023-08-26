package com.johns_group_hr_system.project_employees_assignments.controller;

import com.johns_group_hr_system.project_employees_assignments.dto.EmployeeDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.service.IEmployeeService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> filterEmployees(
            @RequestParam(name = "partialName", required = false) String partialName,
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "projectId", required = false) UUID projectId) {
        List<Employee> filteredEmployees = null;
        try {
            filteredEmployees = employeeService.filterEmployees(partialName, role, projectId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filteredEmployees, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.createEmployee(employeeDto);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/employees/{id}/evaluate")
    public ResponseEntity<Employee> evaluateEmployee(
            @PathVariable UUID id,
            @RequestParam(name = "score") @Min(0) @Max(100) int score) {
        try {
            Employee evaluatedEmployee = employeeService.evaluateEmployee(id, score);
            return new ResponseEntity<>(evaluatedEmployee,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
