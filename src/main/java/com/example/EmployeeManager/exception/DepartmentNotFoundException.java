package com.example.EmployeeManager.exception;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException() {
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
