package com.medicalclinic.handler;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProcessingPatientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleProcessingPatientException(ProcessingPatientException ex) {
        System.out.println(Instant.now());
        return ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Instant.now().toEpochMilli())
                .message(ex.getMessage())
                .build();
    }
}