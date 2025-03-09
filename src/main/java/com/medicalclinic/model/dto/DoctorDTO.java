package com.medicalclinic.model.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder()
@Getter
public class DoctorDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String speciality;
    private Set<Long> facilities;
}