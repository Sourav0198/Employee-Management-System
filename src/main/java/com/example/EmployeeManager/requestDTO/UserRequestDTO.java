package com.example.EmployeeManager.requestDTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
@Data
public class UserRequestDTO {
    @NotBlank(message = "Username must not be blank")
    @Email
    private String userName;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "role name must not be blank")
    private String roleName;
}
