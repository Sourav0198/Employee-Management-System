package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Employee;

import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeResponseHelper {
    public EmployeeResponseDTO getEmployeeDTO(Optional<Employee> employee)
    {
        Employee employee1 = employee.get();
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        BeanUtils.copyProperties(employee1, employeeDTO);
        employeeDTO.setName(employee1.getFirstName() + " " + employee.get().getLastName());
        employeeDTO.setDepartmentName(employee1.getDepartment().getDepartmentName());
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        BeanUtils.copyProperties(employee1.getAddress(),addressResponseDTO);
        employeeDTO.setAddress(addressResponseDTO);
        System.out.println(employee1);
        System.out.println(employeeDTO);
        return employeeDTO;
    }
}
