package com.medicalclinic.model;


import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorMessage(String message, HttpStatus status, long timestamp) {
}
