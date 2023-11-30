package com.example.EmployeeManager.responseDTO;

import com.example.EmployeeManager.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateResponseDTO {

        private Long employeeId;


        private String firstName;

        private String lastName;


        private String email;


        private String phoneNumber;


        private String alternativePhoneNumber;


        private LocalDate hireDate;


        private String jobTitle;


        private double salary;


        private String gender;

        String departmentName;

        AddressResponseDTO addressResponseDTO;

//        private List<Project> projects;



}
