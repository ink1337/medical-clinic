package com.medicalclinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Slf4j
public class ProcessingPatientException extends RuntimeException{
    MessageSource messageSource;


    public ProcessingPatientException(String message) {
        super(message);

    }

    public ProcessingPatientException(String message,  Object ... params) {
        throw new ProcessingPatientException(messageSource.getMessage(message, params, Locale.getDefault()));
    }
}
