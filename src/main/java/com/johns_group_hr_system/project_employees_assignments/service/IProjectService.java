package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.ProjectDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Project;

import java.util.List;

public interface IProjectService {
    Project createProject(ProjectDto dto);

    List<Project> getAllProjects();
}
