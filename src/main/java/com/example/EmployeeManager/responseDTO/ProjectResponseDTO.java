package com.example.EmployeeManager.responseDTO;

import com.example.EmployeeManager.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectResponseDTO {
    private Long projectId;


    private String projectName;


    private List<Long> employeeId;
    private List<String> employeeName;

    private String status;

}



