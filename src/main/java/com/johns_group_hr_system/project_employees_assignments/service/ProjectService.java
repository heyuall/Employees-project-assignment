package com.johns_group_hr_system.project_employees_assignments.service;

import com.johns_group_hr_system.project_employees_assignments.dto.ProjectDto;
import com.johns_group_hr_system.project_employees_assignments.entity.Project;
import com.johns_group_hr_system.project_employees_assignments.repository.IProjectRepository;
import com.johns_group_hr_system.project_employees_assignments.service.helpers.ValidationHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService{

    private final IProjectRepository projectRepository;

    private final ValidationHelper validationHelper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProjectService(IProjectRepository projectRepository, ValidationHelper validationHelper) {
        this.projectRepository = projectRepository;
        this.validationHelper = validationHelper;
    }
    @Override
    public Project createProject(ProjectDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Project name is required.");
        }

        if (dto.getRepo() != null && !dto.getRepo().isEmpty()) {
            if (!validationHelper.isValidURL(dto.getRepo())) {
                throw new IllegalArgumentException("Invalid URL format for the repo.");
            }
        }

        Project project = modelMapper.map(dto, Project.class);
        return projectRepository.save(project);
    }


    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
