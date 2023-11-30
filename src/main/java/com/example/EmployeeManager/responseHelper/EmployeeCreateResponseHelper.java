package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeCreateResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmployeeCreateResponseHelper {

    public EmployeeCreateResponseDTO getEmployeeCreateResponse (Employee employee)
    {
        EmployeeCreateResponseDTO employeeCreateResponseDTO = new EmployeeCreateResponseDTO();
        BeanUtils.copyProperties(employee,employeeCreateResponseDTO);
        employee.getAddress();

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        BeanUtils.copyProperties(employee.getAddress(),addressResponseDTO);
        employeeCreateResponseDTO.setAddressResponseDTO(addressResponseDTO);
        employeeCreateResponseDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        return employeeCreateResponseDTO;


    }
}
