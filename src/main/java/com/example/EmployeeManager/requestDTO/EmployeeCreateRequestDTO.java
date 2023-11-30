package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.entity.Department;
//import com.example.EmployeeManager.entity.LeaveRecord;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.validations.annotations.CustomAnnotations;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeCreateRequestDTO {
   // private Long employeeId;

    @Column(length =20)
    @NotBlank(message="First name cannot be blank")
    private String firstName;

    @Column(length = 20)
    @NotBlank(message = "First name cannot be blank")
    private String lastName;

    @Column (length = 25)
    @NotNull (message= "Please add email")
    @Email(message = "Please enter a valid email")
    private String email;

    @CustomAnnotations.PhoneNumber(message = "please add 10-digit phone number")
    private String phoneNumber;

    @CustomAnnotations.PhoneNumber(message = "Invalid phone number")
//    @NotBlank(message = "Phone number must not be blank")
    private String alternativePhoneNumber;

    @Column(name = "hire_date")
    @NotNull(message = "Hire date cannot be null")
    //@Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    @Column(length = 50)
    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @CustomAnnotations.ValidGender(message = "Please enter a valid gender, either male or female")
    private String gender;

    @NotEmpty(message="Salary should not be null")
    @Min(value = 0, message = "Employee salary can not be negative")
    private double salary;

    //@NotNull(message = "Please add department id")
    private Long departmentId;
    @CustomAnnotations.ValidAddress(message = "please add your address properly")
    private AddressRequestDTO address;
}
