package com.example.EmployeeManager.util;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.DepartmentNotFoundException;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.repository.DepartmentRepo;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;


public class EmployeeUpdateValidationUtils {

    // Private constructor to prevent instantiation
    private EmployeeUpdateValidationUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static void validateEmployeeUpdateByAdmin(Employee oldEmployee, EmployeeUpdateRequestDTOAdmin employeeRequestDTO, DepartmentRepo departmentRepo) throws EmployeeNotFoundException {
        validateEmployee(oldEmployee,employeeRequestDTO);
        validateDepartment(employeeRequestDTO, departmentRepo);
        validateAddress((Address) oldEmployee.getAddress(), (AddressRequestDTO) employeeRequestDTO.getAddress());
    }

    private static void validateEmployee(Employee oldEmployee, EmployeeUpdateRequestDTOAdmin employeeRequestDTO) throws EmployeeNotFoundException {
        if (oldEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if (oldEmployee.getStatus().equals("Inactive")) {
            throw new EmployeeNotFoundException("Employee is not active, unable to update its data!");
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getEmail())) {
            oldEmployee.setEmail(employeeRequestDTO.getEmail());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getPhoneNumber())) {
            oldEmployee.setPhoneNumber(employeeRequestDTO.getPhoneNumber());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getAlternativePhoneNumber())) {
            oldEmployee.setAlternativePhoneNumber(employeeRequestDTO.getAlternativePhoneNumber());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getFirstName())){
            oldEmployee.setFirstName(employeeRequestDTO.getFirstName());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getLastName())) {
            oldEmployee.setLastName(employeeRequestDTO.getLastName());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getGender())) {
            //oldEmployee.setGender(employeeRequestDTO.getGender());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getHireDate())) {
            oldEmployee.setHireDate(employeeRequestDTO.getHireDate());
        }

        if (!StringUtils.isEmpty(employeeRequestDTO.getJobTitle())) {
            oldEmployee.setJobTitle(employeeRequestDTO.getJobTitle());
        }
    }

    private static void validateDepartment(EmployeeUpdateRequestDTOAdmin employeeRequestDTO, DepartmentRepo departmentRepo) {
        if (employeeRequestDTO.getDepartmentId() != null) {
            Optional<Department> newDepartment = departmentRepo.findById(employeeRequestDTO.getDepartmentId());
            if (!newDepartment.isPresent() || newDepartment.get().getStatus().equalsIgnoreCase("Inactive")) {
                throw new DepartmentNotFoundException("Department not found with id " + employeeRequestDTO.getDepartmentId()
                        + " , unable to update the employee details");
            }
        }
    }

    public static void validateAddress(Address oldAddress, AddressRequestDTO updatedAddress) {
        if (updatedAddress != null) {
            System.out.println("updateAddress is not null in the validateAddress() ");
            if (!StringUtils.isEmpty(updatedAddress.getStreet())) {
                oldAddress.setStreet(updatedAddress.getStreet());
            }
            if (!StringUtils.isEmpty(updatedAddress.getState())) {
                oldAddress.setState(updatedAddress.getState());
            }
            if (!StringUtils.isEmpty(updatedAddress.getCity())) {
                oldAddress.setCity(updatedAddress.getCity());
            }
            if (!StringUtils.isEmpty(updatedAddress.getCountry())) {
                oldAddress.setCountry(updatedAddress.getCountry());
            }
            if (!StringUtils.isEmpty(updatedAddress.getPostalCode())) {
                oldAddress.setPostalCode(updatedAddress.getPostalCode());
            }
        }
    }
}
