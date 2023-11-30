package com.example.EmployeeManager.responseDTO;

import com.example.EmployeeManager.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class  AddressResponse {
    private Long employeeId;
    private String employeeName;
    private String email;
    private String phoneNo;
    private Date hireDate;
    private String jobTitle;
    private String departmentName;
    private String addressId;




    // Constructor and getters/setters
}
