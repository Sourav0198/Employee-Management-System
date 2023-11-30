package com.example.EmployeeManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.beans.ConstructorProperties;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
@Builder
@ToString(exclude = "employees")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @NotBlank(message = "projectName must not be blank")
    @Column(name = "project_name", length = 30)
    private String projectName;

    @ManyToMany()
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @Column(name = "status", length = 8)
    private String status;


}
