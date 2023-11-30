package com.example.EmployeeManager.service.projectService;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.exception.ProjectNotFoundException;
import com.example.EmployeeManager.exception.ValidationException;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.repository.ProjectRepo;
import com.example.EmployeeManager.requestDTO.ProjectCreateRequestDTO;
import com.example.EmployeeManager.responseDTO.ProjectResponseDTO;
import com.example.EmployeeManager.responseHelper.ProjectReponseDTOHelper;
import com.example.EmployeeManager.service.employeeService.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ProjectReponseDTOHelper projectReponseDTOHelper;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeService employeeService;
    @Override
    public List<ProjectResponseDTO> getAllProjectDetails() {
        List<Project> project = projectRepo.findAll();
        List<ProjectResponseDTO> responseDTOList = projectReponseDTOHelper.getProjectResponseDTO(project);
        return responseDTOList;

    }

    @Override
    public List<ProjectResponseDTO> getAllActiveProjectDetails() {
        List<Project> project = projectRepo.findAll();
        List<ProjectResponseDTO> responseDTOList = projectReponseDTOHelper.getActiveProjectResponseDTO(project);
        return responseDTOList;
    }

    //problem, exception not cathced
    @Override
    public ProjectResponseDTO getProjectById(Long id) {

        Optional<Project> project = projectRepo.findById(id);
        if(!project.isPresent() || project.get().getStatus().equals("Inactive"))
        {
            throw new ProjectNotFoundException("Project does not exist with id "+id);
        }
        ProjectResponseDTO responseDTO = projectReponseDTOHelper.getProjectDTO(project.get());
        return responseDTO;
    }

    @Override
    public Project addProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public String updateProject(ProjectCreateRequestDTO updateProject, Long id) {
        Optional<Project> project = projectRepo.findById(id);
        if(!project.isPresent() || project.get().getStatus().equals("Inactive"))
        {
            throw new ProjectNotFoundException("Project not found with id "+id);
        }
        project.get().setProjectName(updateProject.getProjectName());
        projectRepo.save(project.get());
        return "Project name updated with name "+ "\""+project.get().getProjectName()+"\"";

    }

    @Override
    public String deleteProjectById(Long id) {
        System.out.println("deleteProjectById called is service layer");
        if(projectRepo.existsById(id)==false)
        {
            System.out.println("project Entity not found exception in delete project by id");
            throw new EntityNotFoundException("Project not found with id "+id);
        }

        Optional<Project> project = projectRepo.findById(id);
        if(!project.get().getEmployees().isEmpty())
        {
            throw new ValidationException("Employee(s) assigned in the project with id "
                    + project.get().getProjectId() + " unable the delete the project");
        }
        if(project.get().getStatus().equals("Active"))
        {
            project.get().setStatus("Inactive");
            System.out.println(project.get());
            projectRepo.save(project.get());
            return "Project with id "+ id + " deleted successfully";
        }
         return "Project with id " + id + " is already deleted";
    }

    @Override
    public String  assignEmployeeToProject(Long employeeId, Long projectId) throws EmployeeNotFoundException {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        if(employee.getStatus().equals("Inactive"))
        {
            throw new EmployeeNotFoundException("Employee was deleted with id "+employeeId);
        }
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if(project.getStatus().equals("Inactive"))
        {
            throw new ProjectNotFoundException("Project was deleted with id"+projectId);
        }
        if(project.getEmployees().contains(employee))
        {
            return "Employee with id "+ employeeId + " already assign with project with id " +projectId;
        }
        employee.getProjects().add(project);
        project.setEmployees(List.of(employee));
        employeeRepo.save(employee);
        return "Employee with id "+ employeeId + " assigned with project with id " +projectId;
    }
    @Override
    @Transactional
    public String  removeEmployeeFromProject(Long employeeId, Long projectId) throws EmployeeNotFoundException {
        System.out.println("removeEmployeeFromProject called");
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id "+employeeId));
//        if(employee.getStatus().equals("Inactive"))
//        {
//            throw new EmployeeNotFoundException("Employee was deleted with id "+employeeId);
//        }
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id "+projectId));

        if(project.getStatus().equals("Inactive"))
        {
            throw new ProjectNotFoundException("Project was deleted with id "+projectId);
        }
        if(project.getEmployees().contains(employee))
        {
            if (employee != null && project != null) {
                // Remove the relationship on both sides
                employee.getProjects().remove(project);
                project.getEmployees().remove(employee);

                // No need to call save; changes will be synchronized by the persistence context
            }
            return "Employee with id "+ employeeId + " is removed from project with id, " +projectId;

        }

        return "Employee with id "+ employeeId + " is currently not assigned with project with id " +projectId;
    }

    @Override
    public ProjectResponseDTO createProject(ProjectCreateRequestDTO requestDTO) {
        Project project = new Project();
        BeanUtils.copyProperties(requestDTO,project);
        project.setStatus("Active");
        Project newProject = projectRepo.save(project);
        ProjectResponseDTO responseDTO = projectReponseDTOHelper.getProjectDTO(project);
        return responseDTO;
    }
}
