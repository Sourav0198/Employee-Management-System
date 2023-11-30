package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsernameIgnoreCase(String username);
}

