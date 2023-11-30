package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.entity.User;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.repository.RoleRepository;
import com.example.EmployeeManager.repository.UserRepository;
import com.example.EmployeeManager.requestDTO.UserRequestDTO;
import com.example.EmployeeManager.service.employeeService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(UserRequestDTO userRequest) throws EmployeeNotFoundException {
        System.out.println(userRequest);
        Employee employee = employeeRepo.findByEmail(userRequest.getUserName());
        if(employee==null)
        {
            throw new EmployeeNotFoundException("Employee not found with id "+ userRequest.getUserName());
        }
        Long employeeId = employee.getEmployeeId();
        // Convert the JSON input to a User entity
        User user = new User(userRequest.getUserName().toUpperCase(), passwordEncoder.encode(userRequest.getPassword()),employeeId);
        // Create or retrieve the associated role
        //String role = userRequest.getRoleName();
        Role role = roleRepository.findByNameIgnoreCase(userRequest.getRoleName());
        if (role == null) {
            role = new Role(userRequest.getRoleName());
            roleRepository.save(role); // Save the role
        }
        // Set the role for the user
        user.setRole(role);
        System.out.println(user);

        // Save the user (including the role due to CascadeType.ALL)
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findByUsernameIgnoreCase(email);
    }
}
