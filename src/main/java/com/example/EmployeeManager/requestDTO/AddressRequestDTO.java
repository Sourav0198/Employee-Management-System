package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
