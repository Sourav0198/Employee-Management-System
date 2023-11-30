package com.example.EmployeeManager.service.addressService;

import com.example.EmployeeManager.entity.Address;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.responseHelper.AddressDTOResponseHelper;
import com.example.EmployeeManager.repository.AddressRepo;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.responseDTO.AddressResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    private EmployeeRepo employeeRepository;
    @Autowired
    private AddressDTOResponseHelper addressDTOResponseHelper;


}
