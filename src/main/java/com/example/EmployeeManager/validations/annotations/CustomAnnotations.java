package com.example.EmployeeManager.validations.annotations;

import com.example.EmployeeManager.validations.validators.Validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Component
public class CustomAnnotations {

    @Target( FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = Validators.PhoneNumberValidator.class)
    public @interface PhoneNumber {
        String message() default "Please enter a valid 10-digit phone number Custum Annotation";
        Class<?>[] groups() default {}; // Define the groups parameter
        Class<? extends Payload>[] payload() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(FIELD)
    public @interface Email {
        String message() default "Please enter a valid email id";
        Class<?>[] groups() default {}; // Define the groups parameter
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(FIELD)
    public @interface NotEmptyString {
        String message() default "Field cannot be empty";
        Class<?>[] groups() default {}; // Define the groups parameter
    }

    @Documented
    @Constraint(validatedBy = Validators.AddressValidator.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ValidAddress {
        String message() default "Please update your address properly";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};


    }

    @Documented
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = Validators.GenderValidator.class)
    public @interface ValidGender {
        String message() default "gender should be male or female only";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = Validators.DepartmentIdValidator.class)
    public @interface ValidDepartmentId {
        String message() default "department id should not be null";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }



}

    // Add more custom annotations...

