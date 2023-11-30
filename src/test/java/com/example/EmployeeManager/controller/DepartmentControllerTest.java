package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.requestDTO.CreateDepartmentRequestDTO;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.service.departmentService.DepartmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;
    private DepartmentResponseDTO departmentResponse;
    @BeforeEach
    void setUp() {
        departmentResponse = DepartmentResponseDTO.builder().departmentId(1L)
                .departmentName("New Department").employeeName(null).employeeId(null)
                .status("Active").build();
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void createDepartment() throws Exception {
        CreateDepartmentRequestDTO departmentRequest = CreateDepartmentRequestDTO.builder().departmentName("New Department").build();
        Mockito.when(departmentService.addDepartment(departmentRequest))
                .thenReturn(departmentResponse);

        mockMvc.perform(post("/departments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"departmentName\": \"new department9081\"\n" +
                                "}")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTb3VyYXYiLCJpYXQiOjE2OTQ1MDM1MzQsImV4cCI6MTY5NDUyMTUzNH0.GUwll8wRnibUuZkiNJmLC8U8LkQsvyrzJIBPKQjkzbClmB20DscgrhuZ-pVwC_AkUPG4sC4VtQENnToQtIFlhg"))
                        .andExpect(status().isCreated());

    }

    @Test
    void testDeleteDepartment() throws Exception {
        // Create a ResponseEntity object that you want the mocked method to return
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department deleted with id 1");
        // Mock the behavior of the service method to return the ResponseEntity
        Mockito.when(departmentService.deleteDepartmentById(anyLong())).thenReturn(expectedResponse);
        // Perform the DELETE request using MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/departments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTb3VyYXYiLCJpYXQiOjE2OTQ1MDM1MzQsImV4cCI6MTY5NDUyMTUzNH0.GUwll8wRnibUuZkiNJmLC8U8LkQsvyrzJIBPKQjkzbClmB20DscgrhuZ-pVwC_AkUPG4sC4VtQENnToQtIFlhg"))
                        .andExpect(status().isNoContent())
                        .andReturn();
        // Verify the response
        assertEquals(204, result.getResponse().getStatus());
        assertEquals("Department deleted with id 1", result.getResponse().getContentAsString());
    }
    @Test
    void testDeleteDepartmentWhenDepartmentNotFound() throws Exception {
        // Create a ResponseEntity object that you want the mocked method to return
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department not found with id 1");
        // Mock the behavior of the service method to return the ResponseEntity
        Mockito.when(departmentService.deleteDepartmentById(anyLong())).thenReturn(expectedResponse);
        // Perform the DELETE request using MockMvc
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/departments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTb3VyYXYiLCJpYXQiOjE2OTQ1MDM1MzQsImV4cCI6MTY5NDUyMTUzNH0.GUwll8wRnibUuZkiNJmLC8U8LkQsvyrzJIBPKQjkzbClmB20DscgrhuZ-pVwC_AkUPG4sC4VtQENnToQtIFlhg"))
                .andExpect(status().isBadRequest())
                .andReturn();
        // Verify the response
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Department not found with id 1", result.getResponse().getContentAsString());
    }
}