package com.example.EmployeeManager.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class JwtResponse {
    private String jwtToken;
    String username;
}
