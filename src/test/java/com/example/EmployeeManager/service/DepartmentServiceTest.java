package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.DepartmentRepo;
import com.example.EmployeeManager.requestDTO.CreateDepartmentRequestDTO;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import com.example.EmployeeManager.service.departmentService.DepartmentService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentServiceTest {
    @Autowired
    DepartmentService departmentService;
    @MockBean
    DepartmentRepo departmentRepo;
    DepartmentResponseDTO expectedDepartmentResponseDTO;

    @BeforeEach
    void setUp() {
        Employee employee = new Employee();
        Department returnDepartment = new Department(1l,"Engineering", null, "Active");
        expectedDepartmentResponseDTO = new DepartmentResponseDTO(1L,"Engineering",new ArrayList<>(),new ArrayList<>(),"Active");
        when(departmentRepo.save(Mockito.any(Department.class))).thenReturn(returnDepartment);
        when(departmentRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(returnDepartment));

    }

    @Test
    void getDepartmentById() {
        DepartmentResponseDTO foundDepartment = departmentService.getDepartmentById(1L);
        assertEquals(expectedDepartmentResponseDTO, foundDepartment);
    }

    @Test
    void addDepartment() {
        CreateDepartmentRequestDTO departmentRequestDTO = new CreateDepartmentRequestDTO("Engineer");
        DepartmentResponseDTO actualResult = departmentService.addDepartment(departmentRequestDTO);
        assertEquals(expectedDepartmentResponseDTO,actualResult);
    }

    @Test
    void deleteDepartmentById() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department deleted with id 1");
        ResponseEntity<String> actualResponse = departmentService.deleteDepartmentById(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}