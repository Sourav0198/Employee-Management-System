package com.example.EmployeeManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
@Builder
//@ToString(exclude = "employees")
//@ToString(exclude = "employees")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("department_Id"), length = 20)
    private Long departmentId;

    @NotBlank(message = "Department name should not be blank")
    @Column(name = ("department_name"), length = 20)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @Column(name = "status", length = 8)
    @NotBlank(message = "department status should not be blank")
    private  String status;

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", employees=" + employees +
                '}';
    }
}
