package com.example.EmployeeManager.service.departmentService;

import com.example.EmployeeManager.requestDTO.CreateDepartmentRequestDTO;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDTO> getAllDepartmentDetails(); //get
    List<DepartmentResponseDTO> getAllActiveDepartmentDetails(); //get
    DepartmentResponseDTO getDepartmentById(Long id); //get
    DepartmentResponseDTO addDepartment(CreateDepartmentRequestDTO requestDTO); //post
    public  String updateDepartment(CreateDepartmentRequestDTO department, Long id); //put
    public ResponseEntity<String> deleteDepartmentById(Long id); //delete

}
