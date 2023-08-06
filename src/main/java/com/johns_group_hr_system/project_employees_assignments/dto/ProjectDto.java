package com.johns_group_hr_system.project_employees_assignments.dto;

import java.util.UUID;

public class ProjectDto {

    private UUID id;
    private String name;
    private String repo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}