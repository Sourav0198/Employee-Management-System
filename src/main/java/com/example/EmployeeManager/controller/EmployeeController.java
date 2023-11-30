package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.User;
import com.example.EmployeeManager.requestDTO.EmployeeCreateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;
import com.example.EmployeeManager.responseDTO.EmployeeCreateResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseoToEmployeeDTO;
import com.example.EmployeeManager.service.UserService;
import com.example.EmployeeManager.service.departmentService.DepartmentService;
import com.example.EmployeeManager.service.employeeService.EmployeeService;
import com.example.EmployeeManager.validations.EmployeeValidation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;

    // API to create a new employee.
    // Accessible only by HR and Admin.
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeCreateRequestDTO newEmployee) {
        log.info("CreateEmployee() method called in controller layer");
        EmployeeCreateResponseDTO employeeCreateResponseDTO = employeeService.addEmployee(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeCreateResponseDTO);
    }
    // API to retrieve all employees and show it to admin and hr with some additional information.
    // Accessible only by HR and Admin.
    @GetMapping("/admin")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesByAdmin() throws EmployeeNotFoundException {
        log.info("getAllEmployeesByAdmin() method called");
        return ResponseEntity.ok(employeeService.getAllEmployeeDetailsByAdmin());
    }
    // API to retrieve all employees with limited information.
    // Accessible by all (HR, Admin, Employee).
    @GetMapping
    public ResponseEntity<List<EmployeeResponseoToEmployeeDTO>> getAllEmployees() throws EmployeeNotFoundException {
        log.info("getAllEmployees method called in controller layer");
        return ResponseEntity.ok(employeeService.getAllEmployeeDetails());
    }
    // API to retrieve an employee by ID with limited information.
    // Accessible by all (HR, Admin, Employee).
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        log.info("getEmployeeById() method called");
        return new ResponseEntity(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }
    // API to retrieve an employee by ID with some additional information.
    // Accessible only by Admin and HR.
    @GetMapping("admin/{employeeId}")
    public ResponseEntity<?> getEmployeeByIdForAdmin(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        log.info("getAllEmployeesByAdmin() called in controller layer");
        return new ResponseEntity(employeeService.getEmployeeByIdForAdmin(employeeId), HttpStatus.OK);
    }
    // API to update an employee's information, an employee can only update his/her limited details.
    // Accessible only by the employee.
    @PutMapping("update/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long employeeId, @RequestBody EmployeeUpdateRequestDTO updatedEmployee,Principal principal) throws EmployeeNotFoundException {
        log.info("updatedEmployee() method called in controller layer");
        String userEmail = (String) principal.getName();
        System.out.println("String : " + userEmail);
        User user = userService.findUserByEmail(userEmail);
        if(user.getEmployeeId()!=employeeId)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee id doesn't match with the currently logged in user's employeeId");
        }
        EmployeeValidation.employeeUpdateByEmployee(updatedEmployee);
        EmployeeResponseDTO updated = employeeService.updateEmployee(updatedEmployee, employeeId);
        return ResponseEntity.ok(updated);
    }
    // API to update an employee's information by Admin or HR, they can update an employee's all the details.
    // Accessible only by Admin and HR.
    @PutMapping("admin/update/{employeeId}")
    public ResponseEntity<?> updateEmployeeByAdmin(
            @PathVariable Long employeeId, @RequestBody EmployeeUpdateRequestDTOAdmin updatedEmployee) throws EmployeeNotFoundException {
        log.info("updatedEmployeeByAdmin() method called in controller layer");
        EmployeeValidation.validateEmployeeUpdateByAdmin(updatedEmployee); // validating employee update request DTO
        EmployeeCreateResponseDTO updated = employeeService.updateEmployeeByAdmin(updatedEmployee, employeeId);
        return ResponseEntity.ok(updated);

    }
    // API to delete an employee by ID.
    // Accessible only by Admin.
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        log.info("deleteEmployee() method called in controller layer");
        return employeeService.deleteEmployeeById(employeeId);
    }
    // API to search employees by first name.
    // Accessible by all (HR, Admin).
    @GetMapping("/admin/search")
    public ResponseEntity<?> searchEmployeesByFirstName(@RequestParam String name) {
        log.info("searchEmployeesByFirstName() method called in controller layer");
        List<EmployeeResponseDTO> responseDTO = employeeService.getEmployeeByFirstName(name);
        if (responseDTO == null || responseDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with name " + name);
        }
        return ResponseEntity.ok(responseDTO);
    }
    // API to retrieve employees by job title.
    // Accessible by all (HR, Admin).
    @GetMapping("/job/{jobTitle}")
    public ResponseEntity<?> getEmployeesByJobTitle(@PathVariable String jobTitle) {
        log.info("getEmployeesByJobTitle() method called in controller layer");
        List<EmployeeResponseDTO> responseDTO = employeeService.findByJobTitleIgnoreCase(jobTitle);
        if (responseDTO == null || responseDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found working as a " + jobTitle);
        }
        return ResponseEntity.ok(responseDTO);
    }
    // API to update an employee's salary.
    // Accessible only by HR.
    @PutMapping("/{employeeId}/salary")
//    @PreAuthorize("hasAnyRole('HR',)")
    public ResponseEntity<String> updateEmployeeSalary(@PathVariable Long employeeId, @RequestBody Map<String, Double> salaryMap) throws EmployeeNotFoundException {
        log.info("updateEmployeeSalary() called in controller layer");
        if (!salaryMap.containsKey("salary")) {
            return ResponseEntity.badRequest().body("Please enter \"salary\" name correctly");
        }
        Long fetchedEmployeeId = employeeService.updateEmployeeSalary(employeeId, salaryMap.get("salary"));
        if (fetchedEmployeeId != null) {
            return ResponseEntity.ok("Salary of employee with id " + employeeId + " is updated ");
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
    // API to retrieve employee statistics.
    // Accessible only by HR.
    @GetMapping("/statistics")
//    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Map<String, Object>> getEmployeeStatistics() {
        log.info("getEmployeeStatistics() method called in controller layer");
        long totalCount = employeeService.countEmployees();
        double averageSalary = employeeService.calculateAverageSalary();
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalEmployees", totalCount);
        statistics.put("averageSalary", averageSalary);
        return ResponseEntity.ok(statistics);
    }
}
