package com.example.EmployeeManager.entity;

//import com.example.EmployeeManager.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import javax.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Builder
//@ToString(exclude = "department")
public class Employee {
    @Id
    @Column(name=("employee_id"))
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name="first_name", length =20)
    @NotBlank(message="First name cannot be blank")
    private String firstName;

    @Column(name = "last_name", length = 20)
    @NotBlank(message = "First name cannot be blank")
    private String lastName;

    @Column(name = "email_id", length = 25)
    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Please enter a valid email")
    private String email;

    @Column(name = "phone_number", length = 10)
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;

    @Column(name="alternative_number", length = 10)

    private String alternativePhoneNumber;

    @Column(name = "hire_date", length = 6)
    @NotNull (message = "Hire date cannot be null")
    //@Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    @Column(name = "job_title", length = 50)
    @NotBlank(message = "Job titile cannot be blank")
    private String jobTitle;

    @Column(name = "salary")
    @Min(0)
    @NotNull (message="Salary should not be null")
    private double salary;


    @Column(name = "gender")
    @NotBlank(message= "gender should not be blank")
    private String gender;

//    @Column(name = "annual_leave_balance")
//    @Min(0)
//    @NotNull(message = "please enter annual leave")
//    private Integer annualLeaveBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @NotNull(message = "please add department for the employee")
    private Department department;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER )
    private List<Project> projects;

    @Column(name = "status", length = 8)
    @NotNull(message = "Employee activation status can not be blank")
    private String status;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alternativePhoneNumber='" + alternativePhoneNumber + '\'' +
                ", hireDate=" + hireDate +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +

                ", address=" + address +
                ", projects=" + projects +
                ", status='" + status + '\'' +
                '}';
    }
}
