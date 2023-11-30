package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.validations.annotations.CustomAnnotations;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUpdateRequestDTO {


    private String email;

    @CustomAnnotations.PhoneNumber
    private String phoneNumber;

    private String alternativePhoneNumber;

    private AddressRequestDTO address;


}
