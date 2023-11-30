package com.example.EmployeeManager.service.projectService;


import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.exception.ProjectNotFoundException;
import com.example.EmployeeManager.requestDTO.ProjectCreateRequestDTO;
import com.example.EmployeeManager.responseDTO.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectResponseDTO> getAllProjectDetails(); //get all
    List<ProjectResponseDTO> getAllActiveProjectDetails(); //get all
    ProjectResponseDTO getProjectById(Long Id) throws ProjectNotFoundException; //get
    public Project addProject(Project project); //post

    public String updateProject(ProjectCreateRequestDTO updateProject, Long id); //put
    public String deleteProjectById(Long Id); //delete

    String assignEmployeeToProject(Long employeeId, Long projectId) throws EmployeeNotFoundException;

    ProjectResponseDTO createProject(ProjectCreateRequestDTO requestDTO);
    public String  removeEmployeeFromProject(Long employeeId, Long projectId) throws EmployeeNotFoundException;






    }
