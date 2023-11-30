package com.example.EmployeeManager.exception;

import javax.management.RuntimeMBeanException;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
