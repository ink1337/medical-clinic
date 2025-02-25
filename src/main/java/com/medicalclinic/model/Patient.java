package com.medicalclinic.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Patient {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String idCardNo;
    private String phoneNumber;

}
