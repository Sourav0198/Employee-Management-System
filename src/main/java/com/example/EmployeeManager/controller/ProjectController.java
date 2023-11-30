package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.requestDTO.ProjectCreateRequestDTO;
import com.example.EmployeeManager.responseDTO.ProjectResponseDTO;
import com.example.EmployeeManager.service.projectService.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("projects")
@Slf4j
public class ProjectController {
    @Autowired
    ProjectService projectService;

    // API to retrieve all projects, including inactive ones.
    // Accessible only by Admin.
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllProjects()
    {
        log.info("getAllProjects() method called in controller layer");
        List<ProjectResponseDTO> responseDTO = projectService.getAllProjectDetails();
        if(responseDTO==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project list is null");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    // API to retrieve all active projects.
    // Accessible by HR and Admin.
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ResponseEntity<List<ProjectResponseDTO>> getAllActiveProjects()
    {
        log.info("getAllActiveProjects() method called in controller layer");
        List<ProjectResponseDTO> responseDTO = projectService.getAllActiveProjectDetails();
        if(responseDTO==null)
        {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Active project list is null");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    // API to retrieve a project by ID only if it is active.
    // Accessible by HR and Admin.
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id)   {
        log.info("getProjectById() method called in controller layer");
        return ResponseEntity.ok().body(projectService.getProjectById(id));
    }
    // API to create a new project.
    // Accessible by HR and Admin.
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ResponseEntity<ProjectResponseDTO> addProject(@RequestBody ProjectCreateRequestDTO requestDTO)
    {
        log.info("addProjects() method called in controller layer");
        ProjectResponseDTO responseDTO = projectService.createProject(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    // API to allocate a project to an employee.
    // Accessible by HR only.
    @PostMapping("/assign")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<String> assignEmployeeToProject(@RequestBody Map<String, Long> requestMap) throws EmployeeNotFoundException {
        log.info("assignEmployeeToProject() method called in controller layer");
        long employeeId = requestMap.getOrDefault("employeeId", -1L);
        long projectId = requestMap.getOrDefault("projectId", -1L);
        if (employeeId == -1L || projectId == -1L) {
            return ResponseEntity.badRequest().body("Invalid input format.");
        }
        String response = projectService.assignEmployeeToProject(employeeId, projectId);
        return ResponseEntity.ok(response);
    }
    // API to remove an employee from a project.
    // Accessible by HR only.
    @DeleteMapping("/remove")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<String> removeEmployeeFromProject(@RequestBody Map<String, Long> requestMap) throws EmployeeNotFoundException {
        log.info("removeEmployeeFromProjects() method called in controller layer");
        long employeeId = requestMap.getOrDefault("employeeId", -1L);
        long projectId = requestMap.getOrDefault("projectId", -1L);
        if (employeeId == -1L || projectId == -1L) {
            return ResponseEntity.badRequest().body("Invalid input format.");
        }
        String response = projectService.removeEmployeeFromProject(employeeId, projectId);
        return ResponseEntity.ok(response);
    }
    // API to delete a project by ID (set as inactive).
    // Accessible only by Admin.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteProjectById(@PathVariable Long id)
    {
        log.info("deleteProjectById() method called in controller layer");
        String response = projectService.deleteProjectById(id);
        return ResponseEntity.ok().body(response);
    }
    // API to update a project's name.
    // Accessible by HR and Admin.
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<String> updateProjectName (@PathVariable Long id, @RequestBody ProjectCreateRequestDTO updateRequestDTO)
    {
        log.info("updateProjectName() method called in controller layer");
        return ResponseEntity.ok().body(projectService.updateProject(updateRequestDTO, id));
    }
}
