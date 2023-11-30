package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {



}
