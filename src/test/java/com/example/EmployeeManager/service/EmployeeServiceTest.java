package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.DepartmentNotFoundException;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;
import com.example.EmployeeManager.responseDTO.*;
import com.example.EmployeeManager.service.employeeService.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;

@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;
    @MockBean
    EmployeeRepo employeeRepo;
    LocalDate hireDate;
    private EmployeeResponseDTO expectedEmployeeResponseDTO;
    private Employee oldEmployee;
    private Department department;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        hireDate = LocalDate.parse("23.12.2023", formatter);
        department = new Department(1L, "Engineering", new ArrayList<>(), "Active");
        expectedEmployeeResponseDTO = new EmployeeResponseDTO(1L, "Sourav Kumar Das", "souravkumardas@gmail.com",
                "7890123451", null, "Female", hireDate, "ASE", "Engineering", new AddressResponseDTO(), "Active");
        oldEmployee = new Employee(1L, "Sourav Kumar", "Das", "souravkumardas@gmail.com", "7890123451",
                null, hireDate, "ASE", 20000, "Female", department, new Address(), null, "Active");
        Employee updatedemployee = new Employee(1L, "Sourav Kumar", "Das", "souravdas@gmail.com", "7890123451",
                null, hireDate, "ASE", 20000, "Male", department, new Address(), null, "Active");
        Mockito.when(employeeRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(oldEmployee));
        Mockito.when(employeeRepo.save(Mockito.any(Employee.class))).thenReturn(updatedemployee);
        Mockito.when(employeeRepo.findAll()).thenReturn(List.of(oldEmployee));
    }

    @Test
    void getAllEmployeeDetailsByAdmin() throws EmployeeNotFoundException {
        EmployeeResponseDTO actualEmployeeResponseDTO = employeeService.getEmployeeByIdForAdmin(1L);
        assertEquals(expectedEmployeeResponseDTO, actualEmployeeResponseDTO);

    }

    @Test
    void deleteEmployeeById() throws EmployeeNotFoundException {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee deleted with id 1");
        ResponseEntity<String> actualResponse = employeeService.deleteEmployeeById(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateEmployeeByAdmin() throws EmployeeNotFoundException {
        EmployeeUpdateRequestDTOAdmin employeeUpdateRequestDTO = EmployeeUpdateRequestDTOAdmin.builder()
                .email("souravdas@gmail.com").gender("Male").departmentId(13L).build();
        EmployeeCreateResponseDTO expectedEmployeeUpdateResponse = new EmployeeCreateResponseDTO(1L, "Sourav Kumar",
                "Das", "souravdas@gmail.com", "7890123451", null,
                hireDate, "ASE", 20000, "Male", "Engineering", new AddressResponseDTO());
        EmployeeCreateResponseDTO actualEmployeeUpdateResponse = employeeService.updateEmployeeByAdmin(employeeUpdateRequestDTO, 1L);
        assertEquals(expectedEmployeeUpdateResponse, actualEmployeeUpdateResponse);

        // Use assertThrows to check if EmployeeNotFoundException is thrown when the department status is Inactive
        assertThrows(EmployeeNotFoundException.class, () -> {
            oldEmployee.setStatus("Inactive");
            employeeService.updateEmployeeByAdmin(employeeUpdateRequestDTO, 1L);
        });
        Mockito.when(employeeRepo.findById(55L)).thenReturn(Optional.empty());
        //Mockito.doThrow(EmployeeNotFoundException.class).when(employeeRepo).findById(55L);

        // Use assertThrows to check if EmployeeNotFoundException is thrown when the department does not exist
        assertThrows(EmployeeNotFoundException.class, () -> {
            //oldEmployee.setStatus("Inactive");
            employeeService.updateEmployeeByAdmin(employeeUpdateRequestDTO, 55L);
        });
    }
    @Test
    void updateEmployee() throws EmployeeNotFoundException {
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO("Mondal Para","Kolkata", "West Bengal", "700078", "India");
        //AddressRequestDTO nullAddressRequestDTO = new AddressRequestDTO();
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO(1L,"Mondal Para", "Kolkata", "West Bengal", "700078", "India");
        EmployeeUpdateRequestDTO employeeUpdateRequestDTO = EmployeeUpdateRequestDTO.builder().address(addressRequestDTO).email("souravdas@gmail.com").build();
        EmployeeResponseDTO employeeResponseDTO;
        Address address = new Address(1L,"Mondal Para","Kolkata","West Bengal", "700078", "India");
        Employee updateEmployee = new Employee(1L, "Sourav Kumar", "Das", "souravdas@gmail.com", "7890123451",
                null, hireDate, "ASE", 20000, "Female", department, address, null, "Active");
        Mockito.when(employeeRepo.save(Mockito.any(Employee.class))).thenReturn(updateEmployee);
        EmployeeResponseDTO actualEmployeeResponse = employeeService.updateEmployee(employeeUpdateRequestDTO, 1L);
        EmployeeResponseDTO expectedEmployeeResponse = new EmployeeResponseDTO(1L, "Sourav Kumar Das", "souravdas@gmail.com",
                "7890123451", null, "Female", hireDate, "ASE", "Engineering", addressResponseDTO, "Active");
        System.out.println("expectedResponse" + expectedEmployeeResponse);
        System.out.println("actualResponse" + actualEmployeeResponse);
        assertEquals(expectedEmployeeResponse, actualEmployeeResponse);
    }
}