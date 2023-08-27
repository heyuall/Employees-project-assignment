/*
package com.johns_group_hr_system.project_employees_assignments.security;

import com.johns_group_hr_system.project_employees_assignments.entity.Employee;
import com.johns_group_hr_system.project_employees_assignments.service.IEmployeeService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService  {

    private final IEmployeeService employeeService;

    public CustomUserDetailsService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   */
/* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.getEmployeeByUsername(username);

        if (employee == null) {
            throw new UsernameNotFoundException("Employee not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(employee.getRole().name()));

        return new User("username", "psd", authorities);
    }*//*

}
*/
