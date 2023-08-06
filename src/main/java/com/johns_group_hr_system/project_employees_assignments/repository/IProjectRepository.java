package com.johns_group_hr_system.project_employees_assignments.repository;

import com.johns_group_hr_system.project_employees_assignments.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProjectRepository extends JpaRepository<Project, UUID> {
}
