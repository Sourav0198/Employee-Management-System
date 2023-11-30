package com.example.EmployeeManager.service.employeeService;

import com.example.EmployeeManager.util.EmployeeUpdateValidationUtils;
import com.example.EmployeeManager.entity.Address;

import com.example.EmployeeManager.exception.DepartmentNotFoundException;
import com.example.EmployeeManager.exception.ValidationException;
import com.example.EmployeeManager.repository.AddressRepo;
import com.example.EmployeeManager.repository.DepartmentRepo;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeCreateRequestDTO;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTO;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exception.EmployeeNotFoundException;
import com.example.EmployeeManager.repository.EmployeeRepo;
import com.example.EmployeeManager.requestDTO.EmployeeUpdateRequestDTOAdmin;
import com.example.EmployeeManager.responseDTO.EmployeeCreateResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseDTO;
import com.example.EmployeeManager.responseDTO.EmployeeResponseoToEmployeeDTO;
import com.example.EmployeeManager.responseHelper.EmployeeCreateResponseHelper;
import com.example.EmployeeManager.responseHelper.EmployeeResponseHelper;
import com.example.EmployeeManager.responseHelper.EmployeeResponseToEmployeeHelper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EntityManager entityManager;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    EmployeeResponseHelper employeeResponseHelper;
    @Autowired
    EmployeeResponseToEmployeeHelper employeeResponseToEmployeeHelper;
    @Autowired
    EmployeeCreateResponseHelper employeeCreateResponseHelper;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Override
    public List<EmployeeResponseDTO> getAllEmployeeDetailsByAdmin() throws EmployeeNotFoundException {

        Optional<List<Employee>> employeeList = Optional.of(employeeRepo.findAll());
        if (!employeeList.isPresent()) {
            throw new EmployeeNotFoundException("No Employee Found");
        }
        List<EmployeeResponseDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList.get()) {
            if(employee.getStatus().equals("Active"))
            {
                employeeDTOList.add(employeeResponseHelper.getEmployeeDTO(Optional.ofNullable(employee)));
            }
        }
        if(employeeDTOList.isEmpty())
        {
            throw new EmployeeNotFoundException("No Employee Found");
        }
        return employeeDTOList;
    }
    @Override
    public List<EmployeeResponseoToEmployeeDTO> getAllEmployeeDetails() throws EmployeeNotFoundException {

        Optional<List<Employee>> employeeList = Optional.of(employeeRepo.findAll());
        if (!employeeList.isPresent()) {
            throw new EmployeeNotFoundException("No Employee Found");
        }
        List<EmployeeResponseoToEmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList.get()) {
            if(employee.getStatus().equals("Active"))
            {
                employeeDTOList.add(employeeResponseToEmployeeHelper.getEmployeeDTO(Optional.ofNullable(employee)));
            }
        }
        if(employeeDTOList.isEmpty())
        {
            throw new EmployeeNotFoundException("No Employee Found");
        }
        return employeeDTOList;
    }

    @Override
    public EmployeeResponseoToEmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent() || employee.get().getStatus().equals("Inactive")) {
            throw new EmployeeNotFoundException("No Employee Found for the employee id " + id);
        }
        System.out.println("Employee object: " + employee);

        EmployeeResponseoToEmployeeDTO employeeDTO = employeeResponseToEmployeeHelper.getEmployeeDTO(employee);

        return employeeDTO;


    }
    @Override
    public EmployeeResponseDTO getEmployeeByIdForAdmin(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException("No Employee Found for the employee id " + id);
        }
        System.out.println("Employee object: " + employee);
        EmployeeResponseDTO employeeDTO = employeeResponseHelper.getEmployeeDTO(employee);
        return employeeDTO;


    }

    //only access to HR who has the right to update employee salary
    @Override
    public Long updateEmployeeSalary(Long employeeId, Double salary) throws EmployeeNotFoundException {

        Optional<Employee> employeeOptional = employeeRepo.findById(employeeId);
        if (!employeeOptional.isPresent()) {
            throw new EmployeeNotFoundException("Employee not found with id " + employeeId);
        } else if (employeeOptional.get().getStatus().equals("Inactive")) {
            throw new EmployeeNotFoundException("Unable to update employee salary because employee" +
                    " is deleted from the database with " + employeeId);
        } else {
            Employee employee = employeeOptional.get();
            employee.setSalary(salary);
            Employee employee1 = employeeRepo.save(employee);
            return employee1.getEmployeeId();
        }
    }

    //To add new employee , only have access to admin and hr
    @Override
    public EmployeeCreateResponseDTO addEmployee(EmployeeCreateRequestDTO employeeCreateRequestDTO) {

        System.out.println("add employee method is called in service layer");
        System.out.println(employeeCreateRequestDTO);
        // Create a new Address and copy properties from the request DTO
        Address newAddress = new Address();
        BeanUtils.copyProperties(employeeCreateRequestDTO.getAddress(), newAddress);
        newAddress.setAddressId(null);

       // Save the Address to generate an address ID
       Address savedAddress = addressRepo.save(newAddress);

       // Create a new Employee and set the address ID
       Employee newEmployee = new Employee();
       newEmployee.setAddress(savedAddress); // Set the address with the generated ID
       if(employeeCreateRequestDTO.getDepartmentId()==null)
       {
          throw new ValidationException("Please enter departmentId");
       }
       Optional<Department> department =departmentRepo.findById(employeeCreateRequestDTO.getDepartmentId());
       if(!department.isPresent() || department.get().getStatus().equalsIgnoreCase("Inactive"))
       {
           throw new DepartmentNotFoundException("Department not found with the following id ,unable to add employee" );
       }

       BeanUtils.copyProperties(employeeCreateRequestDTO, newEmployee);
       newEmployee.setDepartment(department.get());

       System.out.println(newEmployee);
       newEmployee.setStatus("Active");
       System.out.println(newEmployee);
       // Save the Employee with the associated Address
       Employee newEmployeeResponse = employeeRepo.save(newEmployee);

       System.out.println(newEmployeeResponse);
       EmployeeCreateResponseDTO employeeCreateResponseDTO = employeeCreateResponseHelper.getEmployeeCreateResponse(newEmployeeResponse);
       System.out.println(employeeCreateResponseDTO);

       return employeeCreateResponseDTO;
    }

    @Override
    public EmployeeResponseDTO updateEmployee(EmployeeUpdateRequestDTO employeeRequestDTO, Long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> oldEmployeeData = employeeRepo.findById(employeeId);
        if (!oldEmployeeData.isPresent() || oldEmployeeData.get().getStatus().equals("Inactive")) {
            throw new EmployeeNotFoundException("Employee not found with id " + employeeId);
        }
        Employee oldEmployee = oldEmployeeData.get();
        System.out.println("employee request dto" + employeeRequestDTO);
        // Update employee properties
        if (!StringUtils.isEmpty(employeeRequestDTO.getEmail())) {
            oldEmployee.setEmail(employeeRequestDTO.getEmail());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getPhoneNumber())) {
            oldEmployee.setPhoneNumber(employeeRequestDTO.getPhoneNumber());
        }
        if (!StringUtils.isEmpty(employeeRequestDTO.getAlternativePhoneNumber())) {
            oldEmployee.setAlternativePhoneNumber(employeeRequestDTO.getAlternativePhoneNumber());
        }
        EmployeeUpdateValidationUtils.validateAddress(oldEmployee.getAddress(),employeeRequestDTO.getAddress());
        System.out.println("After validating updatedEmployee "+ oldEmployee);
        // Save the modified employee back to the database
        Employee updatedEmployee = employeeRepo.save(oldEmployee);
        EmployeeResponseDTO employeeResponseDTO = employeeResponseHelper.getEmployeeDTO(Optional.of(updatedEmployee));
        return employeeResponseDTO;
    }
    @Override
    public EmployeeCreateResponseDTO updateEmployeeByAdmin(EmployeeUpdateRequestDTOAdmin employeeRequestDTO, Long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> oldEmployeeData = employeeRepo.findById(employeeId);
        System.out.println("Old Employee Data : " + oldEmployeeData);

        if (!oldEmployeeData.isPresent())
        {
            throw new EmployeeNotFoundException("Employee not found with id " + employeeId);
        }

        Employee oldEmployee = oldEmployeeData.get();
        System.out.println("employee request dto" + employeeRequestDTO);
        System.out.println("employeeRequestDTO.getAddress() is not null");
        EmployeeUpdateValidationUtils.validateEmployeeUpdateByAdmin( oldEmployee,employeeRequestDTO,departmentRepo);
        // Save the modified employee back to the database
        System.out.println("Validation successfully done");
        Employee updatedEmployee = employeeRepo.save(oldEmployee);
        System.out.println("update employee "+ updatedEmployee);
        EmployeeCreateResponseDTO responseDTO = employeeCreateResponseHelper.getEmployeeCreateResponse(updatedEmployee);
        return responseDTO;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(Long id) throws EmployeeNotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent()) {
            System.out.println("employee not found exception in deleteemployeebyid service layer");
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }
        Employee deleteEmployee = employee.get();
        if (deleteEmployee.getStatus().equals("Inactive")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee already deleted with id " + id);
        }
        if(deleteEmployee.getProjects()!=null && !deleteEmployee.getProjects().isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee is currently allocated with project(s), Unable to delete employee with " + id);

        }
        deleteEmployee.setStatus("Inactive");
        employeeRepo.save(deleteEmployee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee deleted with id " + id);
    }

    @Override
    public List<Employee> getEmployeeByFirstNameContaining(String fname) {
        return employeeRepo.findEmployeeByFirstNameContaining(fname);
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeeByFirstName(String fname) {

        List<Employee> employeeList = employeeRepo.findEmployeeByFirstNameIgnoreCase(fname);

        if (employeeList != null) {
            List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
            for (Employee employee : employeeList) {
                if (employee.getStatus().equals("Active")) {
                    employeeResponseDTOList.add(employeeResponseHelper.getEmployeeDTO(Optional.ofNullable(employee)));
                }
            }
            return employeeResponseDTOList;
        }
        return null;
    }

    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepo.existsById(employeeId);
    }

    @Override
    public List<Employee> findByDepartment(Department department) {
        return employeeRepo.findByDepartment(department);
    }

    @Override
    public List<EmployeeResponseDTO> findByJobTitleIgnoreCase(String jobTitle) {
        List<Employee> employeeList = employeeRepo.findByJobTitleIgnoreCase(jobTitle);
        if (employeeList != null) {
            List<EmployeeResponseDTO> responseDTOList = new ArrayList<>();
            for (Employee employee : employeeList) {
                if (employee.getStatus().equals("Active")) {
                    responseDTOList.add(employeeResponseHelper.getEmployeeDTO(Optional.of(employee)));
                }
            }
            return responseDTOList;
        }
        return null;
    }

    @Override
    public long countEmployees() {
        return employeeRepo.countActiveEmployees();
    }

    @Override
    public double calculateAverageSalary() {
        return employeeRepo.calculateAverageSalary();
    }

}
