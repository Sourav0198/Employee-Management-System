package com.example.EmployeeManager.responseDTO;



import com.example.EmployeeManager.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private Long employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String gender;
    private LocalDate hireDate;
    private String jobTitle;
    private String departmentName;
    private AddressResponseDTO address;
    private String status;
}
