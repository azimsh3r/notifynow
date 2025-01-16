package com.azimsh3r.apiservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ValidationExceptionHandler {

    public ResponseEntity<Map<String, Object>> handleValidationException(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();

        bindingResult.getFieldErrors().forEach(err -> stringBuilder.append(err.getDefaultMessage()).append("; "));

        return new ResponseEntity<>(
                Map.of(
                        "message", stringBuilder.toString(),
                        "timestamp", LocalDateTime.now()
                ),
                HttpStatus.OK
        );
    }
}
