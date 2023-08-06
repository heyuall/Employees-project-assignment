package com.johns_group_hr_system.project_employees_assignments.service.helpers;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ValidationHelper {
    public boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
