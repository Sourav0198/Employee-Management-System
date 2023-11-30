package com.example.EmployeeManager.validations.validators;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.requestDTO.AddressRequestDTO;
import com.example.EmployeeManager.validations.annotations.CustomAnnotations;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

@Service

public class Validators
{
    public class PhoneNumberValidator implements ConstraintValidator<CustomAnnotations.PhoneNumber, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value == null || value.matches("\\d{10}");
//            return false;
        }
    }

    public class EmailValidator implements ConstraintValidator<Email, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value == null || value.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
        }
    }
    public class AddressValidator implements ConstraintValidator<CustomAnnotations.ValidAddress, AddressRequestDTO> {
        @Override
        public boolean isValid(AddressRequestDTO address, ConstraintValidatorContext context) {
            if (address != null) {
                return !(address.getCity() == null || address.getCity().trim().isEmpty()
                        || address.getCountry() == null || address.getCountry().trim().isEmpty()
                        || address.getPostalCode() == null || address.getPostalCode().trim().isEmpty()
                        || address.getState() == null || address.getState().trim().isEmpty()
                        || address.getStreet() == null || address.getStreet().trim().isEmpty());
            }
            return false;

        }
    }

    public class GenderValidator implements ConstraintValidator<CustomAnnotations.ValidGender, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if(value!=null) {
                if(value.equalsIgnoreCase("Male")|| value.equalsIgnoreCase("Female")){
                    return true;
                }
                return false;
            }
            return true;
        }
    }

    public class DepartmentIdValidator implements ConstraintValidator<CustomAnnotations.ValidDepartmentId, Department> {
        @Override
        public boolean isValid(Department department, ConstraintValidatorContext context) {
            return department == null || department.getDepartmentId() != null;
        }
    }




// Implement more validator classes for other custom annotations...
}


