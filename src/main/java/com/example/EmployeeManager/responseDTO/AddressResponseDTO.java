package com.example.EmployeeManager.responseDTO;

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
public class AddressResponseDTO {
    private Long addressId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;





}
