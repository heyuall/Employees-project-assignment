package com.johns_group_hr_system.project_employees_assignments.repository;

import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.entity.enums.EmployeeRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByNameContainingIgnoreCaseAndRoleAndProjectsContains(String name, EmployeeRoleEnum role, UUID projects_id);

}
