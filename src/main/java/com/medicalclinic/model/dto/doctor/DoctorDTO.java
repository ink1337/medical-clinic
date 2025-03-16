package com.medicalclinic.model.dto.doctor;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class DoctorDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String speciality;
    private Set<Long> facilities;
}