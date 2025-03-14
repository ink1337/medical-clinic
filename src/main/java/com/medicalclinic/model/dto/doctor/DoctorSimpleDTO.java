package com.medicalclinic.model.dto.doctor;


import lombok.Builder;
import lombok.Getter;

@Builder()
@Getter
public class DoctorSimpleDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String speciality;
}