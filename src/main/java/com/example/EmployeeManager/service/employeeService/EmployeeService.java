package com.example.EmployeeManager.service.employeeService;

import com.example.EmployeeManager.requestDTO.EmployeeCreateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;
import com.example.EmployeeManager.responseDTO.EmployeeCreateResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseoToEmployeeDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployeeDetailsByAdmin() throws EmployeeNotFoundException; //get
    List<EmployeeResponseoToEmployeeDTO> getAllEmployeeDetails() throws EmployeeNotFoundException;
    EmployeeResponseoToEmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException; //get
    public EmployeeResponseDTO getEmployeeByIdForAdmin(Long id) throws EmployeeNotFoundException;
    EmployeeCreateResponseDTO addEmployee(EmployeeCreateRequestDTO employee); //post
    EmployeeResponseDTO updateEmployee(EmployeeUpdateRequestDTO employee, Long EmployeeId) throws EmployeeNotFoundException; //put
    ResponseEntity<String> deleteEmployeeById(Long id) throws EmployeeNotFoundException; //delete
    List<Employee> getEmployeeByFirstNameContaining(String fname);
    List<EmployeeResponseDTO> getEmployeeByFirstName(String fname);
    boolean existsById(Long employeeId);
    List<Employee> findByDepartment(Department department);
    List<EmployeeResponseDTO> findByJobTitleIgnoreCase(String jobTitle);

    // Count the total number of employees
    long countEmployees();
    // Calculate the average salary of all employees
    double calculateAverageSalary();
    Long updateEmployeeSalary(Long employeeId, Double salary) throws EmployeeNotFoundException;
    EmployeeCreateResponseDTO updateEmployeeByAdmin(EmployeeUpdateRequestDTOAdmin employeeRequestDTO, Long employeeId) throws EmployeeNotFoundException;
}
