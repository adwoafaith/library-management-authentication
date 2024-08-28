package com.alibou.library.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ConsolidatedErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, StringBuilder> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            if (!errors.containsKey(fieldName)) {
                errors.put(fieldName, new StringBuilder());
            }

            if (errors.get(fieldName).length() > 0) {
                errors.get(fieldName).append(", ");
            }

            errors.get(fieldName).append(errorMessage.replace("Password must contain at least ", ""));
        });

        Map<String, String> formattedErrors = new HashMap<>();
        errors.forEach((fieldName, errorMessage) -> formattedErrors.put(fieldName, errorMessage.toString()));

        return new ResponseEntity<>(formattedErrors, HttpStatus.BAD_REQUEST);
    }
}