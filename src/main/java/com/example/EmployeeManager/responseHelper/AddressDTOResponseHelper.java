package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.responseDTO.AddressResponse;

import com.example.EmployeeManager.service.employeeService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;
@Component
public class AddressDTOResponseHelper {
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeService employeeService;

//    public AddressResponse getAddressResponse (Optional<Address> address) throws EmployeeNotFoundException {
//        Long employeeId = address.get().getEmployee().getEmployeeId();
//        EmployeeDTO employeeOptional = employeeService.getEmployeeById(employeeId);
//        System.out.println(employeeOptional);
//        AddressResponse addressResponse = new AddressResponse();
////        BeanUtils.copyProperties(address , addressResponse);
////        BeanUtils.copyProperties(employeeOptional,addressResponse);
//        addressResponse.setEmployeeId(employeeOptional.getEmployeeId());
//        addressResponse.setEmployeeName(employeeOptional.getName());
//        addressResponse.setDepartmentName(employeeOptional.getDepartmentName());
//        addressResponse.setAddressId(String.valueOf((Long)address.get().getAddressId()));
//        addressResponse.setEmail(employeeOptional.getEmail());
//        addressResponse.setPhoneNo(employeeOptional.getPhoneNumber());
//        addressResponse.setHireDate((Date) employeeOptional.getHireDate());
//        addressResponse.setDepartmentName(employeeOptional.getDepartmentName());
//        addressResponse.setJobTitle(employeeOptional.getJobTitle());
//        System.out.println(addressResponse);
//        return addressResponse;
//    }
}
