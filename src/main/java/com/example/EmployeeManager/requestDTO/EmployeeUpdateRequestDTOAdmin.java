package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.entity.Department;
//import com.example.EmployeeManager.entity.LeaveRecord;
import com.example.EmployeeManager.entity.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUpdateRequestDTOAdmin {
    // private Long employeeId;

    @Column(length =20)
    @NotBlank(message="First name cannot be blank")
    private String firstName;

    @Column(length = 20)
    @NotBlank(message = "First name cannot be blank")
    private String lastName;

    @Column (length = 25)
    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Please enter a valid email")
    private String email;

    @Column(length = 10)
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;

    @Column(length = 10)
//    @NotBlank(message = "Phone number must not be blank")
    private String alternativePhoneNumber;

    @Column(name = "hire_date", length = 6)
    @NotNull(message = "Hire date cannot be null")
    //@Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    @Column(length = 50)
    @NotBlank(message = "Job titile cannot be blank")
    private String jobTitle;

    @Column(length = 6)
    @NotBlank(message = "gender should not be blank")
    private String gender;

//    @NotNull(message = "Department should be added with employee")
    private Long departmentId;
    @NotNull(message = "Please add address of the employee")
    private AddressRequestDTO address;
}
