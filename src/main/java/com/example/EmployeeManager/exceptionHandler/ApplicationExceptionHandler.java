package com.example.EmployeeManager.exceptionHandler;

import com.example.EmployeeManager.exception.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException.class called: " +  ex.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
        });


        return errorMap;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AddressNotFoundException.class)
    public Map<String,String> handleUserNotFoundException(AddressNotFoundException ex) {
        log.error("AddressNotFoundException.class called: " + ex.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    //for this exception we need to declare bean and enable mock mvc

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException.class called: " + ex.getMessage());
        String errorMessage = "Resource not found for the provided URL: " + ex.getRequestURL();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorMessage);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException called: " + ex.getMessage());
        String supportedMethods = String.join(", ", ex.getSupportedMethods());
        String errorMessage = "HTTP method not allowed. Supported methods: " + supportedMethods;
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorMessage);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleValidationExceptions(NumberFormatException ex) {
        log.error("NumberFormatException.Class called: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested URL doesn't match properly \n"+"message: "+ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public Map<String,String> handleUserNotFoundException(EmployeeNotFoundException ex) {
        log.error("EmployeeNotFoundException called: " + ex.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ResponseBody
    public Map<String, String> handleValidationException(ValidationException ex) {
        log.error("ValidationException called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException called: " + ex.getMessage());
        // Extract the error message from the first violation
        String errorMessage = ex.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> violation.getMessage())
                .orElse("Validation failed");
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleSQLConstraintValidationException(DataIntegrityViolationException ex) {
        log.error("DataIntegrityException called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProjectNotFoundException.class)
    public Map<String, String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        log.error("ProjectNotFoundException.class called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DepartmentNotFoundException.class)
    public Map<String, String> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        log.error("DepartmentNotFoundException.class called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, String> accessDeniedExceptionHandler(AccessDeniedException ex) {
        log.error("AccessDeniedException called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> defaultExceptionHandler(Exception ex) {
        log.error("Exception.class called: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return errorResponse;
    }
}
