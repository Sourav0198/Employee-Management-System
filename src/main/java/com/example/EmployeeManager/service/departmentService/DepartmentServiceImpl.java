package com.example.EmployeeManager.service.departmentService;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.DepartmentNotFoundException;
import com.example.EmployeeManager.repository.DepartmentRepo;
import com.example.EmployeeManager.requestDTO.CreateDepartmentRequestDTO;
import com.example.EmployeeManager.requestHelper.DepartmentEmployee;
import com.example.EmployeeManager.responseDTO.DepartmentResponseDTO;
import com.example.EmployeeManager.responseHelper.DepartmentResponseHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    DepartmentResponseHelper responseHelper;


    @Override
    public List<DepartmentResponseDTO> getAllDepartmentDetails() {

        List<Department> departmentList = departmentRepo.findAll();
        List<DepartmentResponseDTO> responseDTOList = new ArrayList<>();
        for(Department department : departmentList)
        {

            DepartmentResponseDTO departmentResponse = responseHelper.getDepartmentResponse(department);
            List<Long> employeeIds = new ArrayList<>();
            List<String> employeeNames = new ArrayList<>();
            for(Employee employee : department.getEmployees())
            {
                 employeeIds.add(employee.getEmployeeId());
                 employeeNames.add(employee.getFirstName() + " " + employee.getLastName());
            }

            departmentResponse.setEmployeeId(employeeIds);
            departmentResponse.setEmployeeName(employeeNames);
            responseDTOList.add(departmentResponse);
        }
        return responseDTOList;

    }
    @Override
    public List<DepartmentResponseDTO> getAllActiveDepartmentDetails() {

        List<Department> departmentList = departmentRepo.findAll();
        List<DepartmentResponseDTO> responseDTOList = new ArrayList<>();
        for(Department department : departmentList)
        {
            if(department.getStatus().equals("Active"))
            {
                DepartmentResponseDTO departmentResponse = responseHelper.getDepartmentResponse(department);
//                List<Long> employeeIds = new ArrayList<>();
//                List<String> employeeNames = new ArrayList<>();
//                for(Employee employee : department.getEmployees())
//                {
//                    employeeIds.add(employee.getEmployeeId());
//                    employeeNames.add(employee.getFirstName() + " " + employee.getLastName());
//                }
//
//                departmentResponse.setEmployeeId(employeeIds);
//                departmentResponse.setEmployeeName(employeeNames);
                responseDTOList.add(departmentResponse);
            }

        }
        return responseDTOList;

    }

    @Override
    public DepartmentResponseDTO getDepartmentById(Long id) {
        Optional<Department> department = departmentRepo.findById(id);
        if(!department.isPresent() || department.get().getStatus().equals("Inactive"))
        {
            throw new DepartmentNotFoundException("Department not found with id "+id);
        }
        DepartmentResponseDTO responseDTO = responseHelper.getDepartmentResponse(department.get());
        return responseDTO;
    }

    @Override
    public DepartmentResponseDTO addDepartment(CreateDepartmentRequestDTO requestDTO) {
        Department newDepartment = new Department();
        BeanUtils.copyProperties(requestDTO,newDepartment);
        newDepartment.setStatus("Active");
//        System.out.println(newDepartment);
//        System.out.println(requestDTO);
        Department savedDepartment = departmentRepo.save(newDepartment);
        System.out.println("Department service called for add department");
        DepartmentResponseDTO responseDTO = responseHelper.getDepartmentResponse(savedDepartment);
        return responseDTO;

    }

    @Override
    public String updateDepartment(CreateDepartmentRequestDTO department, Long id) {
        Optional<Department> updateDepartment = departmentRepo.findById(id);
        if(!updateDepartment.isPresent() || updateDepartment.get().getStatus().equals("Inactive"))
        {
            throw new DepartmentNotFoundException("Department not found with id "+id);
        }
        BeanUtils.copyProperties(department,updateDepartment.get());
        departmentRepo.save(updateDepartment.get());
        return "Department updated with name "+department.getDepartmentName();

    }

    @Override
    public ResponseEntity<String> deleteDepartmentById(Long id) {
        Optional<Department> department = departmentRepo.findById(id);
        if(!department.isPresent())
        {
            throw new DepartmentNotFoundException("Department not found with id "+id);
        }
        if(department.get().getStatus().equals("Inactive"))
        {
            return ResponseEntity.badRequest().body("Department already deleted with id "+id);
        }
        if(DepartmentEmployee.isActiveEmployeeInDepartment(department.get()))
        {
            return ResponseEntity.badRequest().body("Unable to delete department because employee is already assigned in it");
        }
        department.get().setStatus("Inactive");
        departmentRepo.save(department.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department deleted with id "+id);
    }
}
