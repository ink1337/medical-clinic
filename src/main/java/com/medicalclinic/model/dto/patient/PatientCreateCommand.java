package com.medicalclinic.model.dto.patient;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class PatientCreateCommand {
    private final String email;
    private final String firstName;
    private final String lastName;
    private String password;
    private final LocalDate birthday;
    private final String idCardNo;
    private final String phoneNumber;

}
