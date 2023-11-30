package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
// org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    //Optional<Employee> findByEmployeeIdAndStatusIsActive(Long id);
    List<Employee> findEmployeeByFirstNameContaining(String fName);
    List<Employee> findEmployeeByFirstNameIgnoreCase(String fname);
    boolean existsById(Long employeeId);

//    List<LeaveRecord> findLeavesByEmployeeId(Long employeeId);
    List<Employee> findByDepartment(Department department);

    List<Employee> findByJobTitleIgnoreCase(String jobTitle);


    // Count the total number of employees
    @Query("SELECT COUNT(*) FROM Employee e WHERE e.status = 'Active'")
    long countActiveEmployees();
    // Calculate the average salary of all employees
    @Query("SELECT ROUND(AVG(e.salary),2) FROM Employee e where e.status = 'Active'")
    double calculateAverageSalary();

    Employee findByEmail(String email);

    // Find employees on leave today
//    @Query("SELECT DISTINCT e FROM Employee e JOIN e.leaves l " +
//            "WHERE :today BETWEEN l.startDate AND l.endDate")
//    List<Employee> findEmployeesOnLeaveToday(Date today);
//


}
