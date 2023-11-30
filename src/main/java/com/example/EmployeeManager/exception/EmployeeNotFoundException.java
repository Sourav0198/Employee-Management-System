package com.example.EmployeeManager.exception;

import javax.management.RuntimeMBeanException;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
