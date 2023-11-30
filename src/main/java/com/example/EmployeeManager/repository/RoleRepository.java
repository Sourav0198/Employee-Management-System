package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNameIgnoreCase(String name);
}
