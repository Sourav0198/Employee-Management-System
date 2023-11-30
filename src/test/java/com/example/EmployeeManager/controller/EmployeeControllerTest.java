package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.requestDTO.EmployeeCreateRequestDTO;
import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeCreateResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.service.employeeService.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
@SpringBootTest
//@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {
//    @Autowired
//    private EmployeeController employeeController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    private  EmployeeResponseDTO sampleEmployeeResponse;
    private EmployeeCreateResponseDTO sampleEmployeeCreateResponse;
    EmployeeCreateRequestDTO sampleEmployeeCreateRequest;

    @BeforeEach
    void setUp() {
        // Create a sample Employee object for testing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate hireDate = LocalDate.parse("23.12.2023", formatter);
        sampleEmployeeResponse = new EmployeeResponseDTO(1L,"Sourav Kumar Das"
                ,"souravkumardas7059@gmail.com","7890234123",
                null,"Male",hireDate,"ASE",
                "Engineering",null,"Active");
        sampleEmployeeCreateRequest = new EmployeeCreateRequestDTO("Sourav Kumar","Das",
                "souravkumardas7059@gmail.com","7890234123",null,hireDate,"ASE","Male",
                20000,null,null);
        sampleEmployeeCreateResponse = new EmployeeCreateResponseDTO(1L,"Sourav Kumar",
                "Das","souravkumardas7059@gmail.com","7890234123",null,hireDate,"ASE",
                20000, "Male","IT",null);
    }
    @Test
    void createEmployee() throws Exception {
        // Create a sample request JSON
        String requestJson = "{" +
                "\"firstName\":\"Jeevan\"," +
                "\"lastName\":\"Das\"," +
                "\"email\":\"jeevandas@gmail.com\"," +
                "\"phoneNumber\":\"2170903056\"," +
                "\"alternativePhoneNumber\":\"7890123457\"," +
                "\"gender\":\"Male\"," +
                "\"hireDate\":\"2023-09-07\"," +
                "\"jobTitle\":\"Human Resource Associate\"," +
                "\"salary\":90.0," +
                "\"departmentId\":2," +
                "\"address\":{" +
                "\"addressId\":52," +
                "\"street\":\"uk street\"," +
                "\"city\":\"new delhi\"," +
                "\"state\":\"delhi\"," +
                "\"postalCode\":\"678901\"," +
                "\"country\":\"india\"" +
                "}" +
                "}";
        // Mock the behavior of employeeService.addEmployee
        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeCreateRequestDTO.class)))
                .thenReturn(sampleEmployeeCreateResponse);

        // Perform the test using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJOZWhhIiwiaWF0IjoxNjk0NjA3NDc1LCJleHAiOjE2OTY0MDc0NzV9.7NZk_d5gGqsudSjGcmDHCdAeQlc6PZT3Afa8TwdQzuysoA5P1pEWaeJKkguAESQf47wZxkpE-3rgRNcJsp8XZg"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Sourav Kumar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Das"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("souravkumardas7059@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("7890234123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value("IT"));
    }



    @Test
    void testGetEmployeeByIdForAdmin() throws Exception {
        // Mock the behavior of employeeService.getEmployeeByIdForAdmin
        Mockito.when(employeeService.getEmployeeByIdForAdmin(anyLong()))
                .thenReturn(sampleEmployeeResponse);

        // Perform the test using MockMvc
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/employee/admin/1")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTb3VyYXYiLCJpYXQiOjE2OTQ2MDY5MDUsImV4cCI6MTY5NjQwNjkwNX0.n_XJAUzV_dff9-uhg-uvzrKZKuMdkrXBXkGwP-fMDrG1-9HMHv4O4_gCXfwLvvbffFd1wJJIlfe5ZYrJxWqddg"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sourav Kumar Das"));

        // Perform the test
        //ResponseEntity<?> responseEntity = employeeController.getEmployeeByIdForAdmin(1L);

        // Verify the response status code
        //assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the response body
//        EmployeeResponseDTO responseEmployee = (EmployeeResponseDTO) responseEntity.getBody();
//        assertEquals(1L, responseEmployee.getEmployeeId());
//        assertEquals("Sourav Kumar Das", responseEmployee.getName());
    }

}