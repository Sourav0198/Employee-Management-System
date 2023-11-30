package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseoToEmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class EmployeeResponseToEmployeeHelper {
    public EmployeeResponseoToEmployeeDTO getEmployeeDTO(Optional<Employee> employee)
    {
        Employee employee1 = employee.get();
        EmployeeResponseoToEmployeeDTO employeeDTO = new EmployeeResponseoToEmployeeDTO();
        BeanUtils.copyProperties(employee1, employeeDTO);
        employeeDTO.setName(employee1.getFirstName() + " " + employee.get().getLastName());
        employeeDTO.setDepartmentName(employee1.getDepartment().getDepartmentName());
       // AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        //BeanUtils.copyProperties(employee1.getAddress(),addressResponseDTO);
        //employeeDTO.setAddress(addressResponseDTO);
        //System.out.println(employee1);
        //System.out.println(employeeDTO);
        return employeeDTO;
    }
}
