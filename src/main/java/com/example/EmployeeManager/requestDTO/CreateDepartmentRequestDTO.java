package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateDepartmentRequestDTO {

    @NotBlank(message = "Department name should not be blank")
    @Column(length = 20)
    private String departmentName;

}
