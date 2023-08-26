package com.johns_group_hr_system.project_employees_assignments.controller;

import com.johns_group_hr_system.project_employees_assignments.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/assign/{employeeId}")
    public ResponseEntity<Void> assignProjectsToEmployee(
            @PathVariable UUID employeeId,
            @RequestBody List<UUID> projectIds) {
        try {
            employeeService.assignProjectsToEmployee(employeeId, projectIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unassign/{employeeId}")
    public ResponseEntity<Void> unassignProjectsFromEmployee(
            @PathVariable UUID employeeId,
            @RequestBody List<UUID> projectIds) {
        try {
            employeeService.unAssignProjectsFromEmployee(employeeId, projectIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
