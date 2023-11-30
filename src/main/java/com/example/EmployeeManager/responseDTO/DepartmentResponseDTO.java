package com.example.EmployeeManager.responseDTO;

import com.example.EmployeeManager.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponseDTO {

    private Long departmentId;


    private String departmentName;

    private List<Long> employeeId;
    private List<String> employeeName;

    private String status;

}


