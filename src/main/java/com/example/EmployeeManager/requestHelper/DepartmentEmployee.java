package com.example.EmployeeManager.requestHelper;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;

import java.util.List;

public class DepartmentEmployee{
    private DepartmentEmployee()
    {
        System.out.println("we can't call private constructor!");
    }

    public static boolean isActiveEmployeeInDepartment(Department department) {
        List<Employee> employeeList =  department.getEmployees();
        if(employeeList== null)
        {
            return false;
        }
        if(employeeList.isEmpty()) {
            return false;
        }
        for(Employee employee : employeeList) {
            if(employee.getStatus().equals("Active")) {
                return true;
            }
        }
            return false;
    }
}
