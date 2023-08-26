package com.johns_group_hr_system.project_employees_assignments.entity.enums;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

public enum EmployeeRoleEnum {
    DEVELOPER(0,"developer"),
    PROJECT_MANAGER(1, "projectManager"),
    HR(2, "hr");

    private int id;
    private String value;
    EmployeeRoleEnum(int id, String value){
        this.id = id;
        this.value = value;
    }

    private static final Map<String, EmployeeRoleEnum> LOOKUP = Maps.uniqueIndex(
            Arrays.asList(EmployeeRoleEnum.values()),
            EmployeeRoleEnum::getValue
    );

    public static EmployeeRoleEnum fromName(String name) throws NullPointerException{
        EmployeeRoleEnum role = LOOKUP.get(name);
        if (role == null){
            throw new NullPointerException(String.format("%s is not a value in EmployeeRoleEnum ", name));
        }
        return LOOKUP.get(name);
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
