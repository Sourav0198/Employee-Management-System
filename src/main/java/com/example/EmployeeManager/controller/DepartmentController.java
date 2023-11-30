package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.requestDTO.CreateDepartmentRequestDTO;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import com.example.EmployeeManager.service.departmentService.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    // API to create a new department.
    // Accessible only by Admin.
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody CreateDepartmentRequestDTO requestDTO) {
        log.info("createDepartment() method called in controller layer");
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addDepartment(requestDTO));
    }
    // API to retrieve all departments, including deleted ones.
    // Accessible only by Admin.
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments()
    {
        log.info("getALLDepartment() method called in controller layer");
        List<DepartmentResponseDTO> responseDTO = departmentService.getAllDepartmentDetails();
        if(responseDTO==null)
        {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department list is null");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    // API to retrieve all active departments.
    // Accessible by HR and Admin.
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ResponseEntity<List<DepartmentResponseDTO>> getAllActiveDepartments()
    {
        log.info("getALLActiveDepartment() method called in controller layer");
        List<DepartmentResponseDTO> responseDTO = departmentService.getAllActiveDepartmentDetails();
        if(responseDTO==null)
        {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Active department list is null");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    // API to retrieve a department by ID only if it is active.
    // Accessible by HR and Admin.
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ResponseEntity<DepartmentResponseDTO> getProjectById(@PathVariable Long id)   {
        log.info("getProjectById() method called in controller layer");
        return ResponseEntity.ok().body(departmentService.getDepartmentById(id));
    }
    // API to update the department's name.
    // Accessible only by Admin.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> updateDepartmentName (
            @RequestBody CreateDepartmentRequestDTO updateRequestDTO,
            @PathVariable Long id)
    {
        log.info("updateDepartmentName() method called in controller layer");
        return ResponseEntity.ok().body(departmentService.updateDepartment(updateRequestDTO, id));
    }
    // API to delete a department by ID (set as inactive).
    // Accessible only by Admin.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id)
    {
        log.info("deleteDepartment() method called in controller layer");
        departmentService.deleteDepartmentById(id);
        return departmentService.deleteDepartmentById(id);
    }

}
