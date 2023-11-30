package com.example.EmployeeManager.responseHelper;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.responseDTO.AddressResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.responseDTO.ProjectResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectReponseDTOHelper {
    public List<ProjectResponseDTO> getProjectResponseDTO (List<Project> projectList) {
        return projectList.stream()
                .map(project -> {
                    ProjectResponseDTO responseDTO = new ProjectResponseDTO();
                    responseDTO.setProjectId(project.getProjectId());
                    responseDTO.setProjectName(project.getProjectName());
                    responseDTO.setStatus(project.getStatus());
                    List<Long> employeeId = new ArrayList<>();
                    List<String> employeeName = new ArrayList<>();

                    List<EmployeeResponseDTO> employees = project.getEmployees().stream()
                            .map(employee -> {
                                EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
                                employeeName.add(employee.getFirstName()+" "+employee.getLastName());
                                employeeId.add(employee.getEmployeeId());
                                // ... other employee mappings
                                return employeeResponseDTO;
                            })
                            .collect(Collectors.toList());

                    responseDTO.setEmployeeId(employeeId);
                    responseDTO.setEmployeeName(employeeName);

                    // ... other project mappings
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

    public List<ProjectResponseDTO> getActiveProjectResponseDTO(List<Project> projectList) {
        return projectList.stream()
                .filter(project -> project.getStatus().equals("Active"))
                .map(project -> {
                    ProjectResponseDTO responseDTO = new ProjectResponseDTO();
                    responseDTO.setProjectId(project.getProjectId());
                    responseDTO.setProjectName(project.getProjectName());
                    responseDTO.setStatus(project.getStatus());
                    List<Long> employeeId = new ArrayList<>();
                    List<String> employeeName = new ArrayList<>();

                    if (project.getStatus().equals("Active")) {
                        List<EmployeeResponseDTO> employees = project.getEmployees().stream()
                                .map(employee -> {
                                    EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
                                    employeeName.add(employee.getFirstName() + " " + employee.getLastName());
                                    employeeId.add(employee.getEmployeeId());

                                    return employeeResponseDTO;
                                })
                                .collect(Collectors.toList());

                        responseDTO.setEmployeeId(employeeId);
                        responseDTO.setEmployeeName(employeeName);
                    }

                    // ... other project mappings
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
    public ProjectResponseDTO getProjectDTO(Project project)
    {
        ProjectResponseDTO responseDTO = new ProjectResponseDTO();
        BeanUtils.copyProperties(project,responseDTO);
        List<Employee> employee = project.getEmployees();
        List<Long> employeeIds = new ArrayList<>();
        List<String> employeeNames = new ArrayList<>();
        // For Each Loop for iterating ArrayList
        for (Long id : employeeIds){
            employeeIds.add(id);
        }
        for (String name : employeeNames){
            employeeNames.add(name);
        }
        responseDTO.setEmployeeId(employeeIds);
        responseDTO.setEmployeeName(employeeNames);
        return responseDTO;

    }

}
