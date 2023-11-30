package com.example.EmployeeManager.requestDTO;

import com.example.EmployeeManager.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString()
    public class ProjectCreateRequestDTO {

        private Long projectId;

        @NotBlank(message = "projectName must not be blank")
        @Column(name = "project_name", length = 30)
        private String projectName;

        private String status;




}
