package br.com.caelum.mamute.infrastructure.controller.advice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorDetails>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final List<ErrorDetails> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            final String errorMessage = error.getDefaultMessage();
            final String fieldName = ((FieldError) error).getField();
            errors.add(new ErrorDetails(fieldName + ": " + errorMessage, "", UUID.randomUUID().toString(),
                    LocalDateTime.now()));
        });

        errors.forEach(e -> log.error(e.toString()));
        return ResponseEntity.badRequest().body(errors);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorDetails {
        public String message;
        public String details;
        public String correlationID;
        public LocalDateTime timestamp;
    }
}
