package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentResponseHelper {
    public DepartmentResponseDTO getDepartmentResponse(Department department)
    {
        DepartmentResponseDTO departmentResponse = new DepartmentResponseDTO();
        BeanUtils.copyProperties(department,departmentResponse);
        List<Employee> employeeList = department.getEmployees();
        List<Long> employeeIds = new ArrayList<>();
        List<String>  employeeNames = new ArrayList<>();
        if(employeeList!=null)
        {
            for(Employee employee : employeeList)
            {
                employeeIds.add(employee.getEmployeeId());
                employeeNames.add(employee.getFirstName() + " " + employee.getLastName());
            }
        }

        departmentResponse.setEmployeeId(employeeIds);
        departmentResponse.setEmployeeName(employeeNames);
        return departmentResponse;
    }
}
