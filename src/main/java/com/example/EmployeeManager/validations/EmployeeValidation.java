package com.example.EmployeeManager.validations;

import com.example.EmployeeManager.exception.ValidationException;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;

public class EmployeeValidation {
    public static void validateEmployeeUpdateByAdmin(EmployeeUpdateRequestDTOAdmin updatedEmployee)
    {
        if (updatedEmployee.getPhoneNumber() != null && !updatedEmployee.getPhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Please enter a valid 10-digit phone number");
        }
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            throw new ValidationException("Please enter a valid email id");
        }
        if (updatedEmployee.getAlternativePhoneNumber() != null && !updatedEmployee.getAlternativePhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Please enter a valid 10-digit alternative phone number");
        }
        if (updatedEmployee.getGender() != null && updatedEmployee.getGender().trim().isEmpty()) {
            throw new ValidationException("gender should not be null");
        }
        if (updatedEmployee.getFirstName() != null && updatedEmployee.getFirstName().trim().isEmpty()) {
            throw new ValidationException("First name should not be null");
        }
        if (updatedEmployee.getLastName() != null && updatedEmployee.getLastName().trim().isEmpty()) {
            throw new ValidationException("Last name should not be null");
        }
        if (updatedEmployee.getJobTitle() != null && updatedEmployee.getJobTitle().trim().isEmpty()) {
            throw new ValidationException("Job title cannot be null");
        }
        AddressRequestDTO address = updatedEmployee.getAddress();
        System.out.println(address);
        if (address != null && ((address.getCity() != null && address.getCity().trim().isEmpty()) ||
                (address.getCountry() != null && address.getCountry().trim().isEmpty())
                || (address.getPostalCode() != null && address.getPostalCode().trim().isEmpty()) ||
                (address.getState() != null && address.getState().trim().isEmpty())
                || (address.getState() != null && address.getStreet().trim().isEmpty()))) {
            throw new ValidationException("Please update your address properly");
        }
    }
    public static void employeeUpdateByEmployee(EmployeeUpdateRequestDTO updatedEmployee)
    {
        if (updatedEmployee.getPhoneNumber() != null && !updatedEmployee.getPhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Please enter a valid 10-digit phone number");
        }
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            throw new ValidationException("Please enter a valid email id");
        }
        if (updatedEmployee.getAlternativePhoneNumber() != null && !updatedEmployee.getAlternativePhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Please enter a valid 10-digit alternative phone number");
        }
        AddressRequestDTO address = updatedEmployee.getAddress();
        System.out.println(address);
        if (address != null && ((address.getCity() != null && address.getCity().trim().isEmpty()) ||
                (address.getCountry() != null && address.getCountry().trim().isEmpty())
                || (address.getPostalCode() != null && address.getPostalCode().trim().isEmpty()) ||
                (address.getState() != null && address.getState().trim().isEmpty())
                || (address.getState() != null && address.getStreet().trim().isEmpty()))) {
            throw new ValidationException("Please update your address properly");
        }
    }
}
