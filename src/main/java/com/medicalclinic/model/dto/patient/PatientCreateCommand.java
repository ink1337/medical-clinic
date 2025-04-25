package com.medicalclinic.model.dto.patient;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class PatientCreateCommand {
    private final String email;
    private final String firstName;
    private final String lastName;
    private String password;
    private final LocalDate birthday;
    private final String idCardNo;
    private final String phoneNumber;

}
