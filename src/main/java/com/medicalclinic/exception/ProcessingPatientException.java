package com.medicalclinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Slf4j
public class ProcessingPatientException extends RuntimeException {

    public ProcessingPatientException(String message) {
        super(message);
    }
}
