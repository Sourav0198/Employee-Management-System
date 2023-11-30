package com.example.EmployeeManager.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseoToEmployeeDTO {
    private Long employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String gender;
    private LocalDate hireDate;
    private String jobTitle;
    private String departmentName;

}