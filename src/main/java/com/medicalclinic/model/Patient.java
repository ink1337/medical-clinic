package com.medicalclinic.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Patient {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long idCardNo;
    private String phoneNumber;

}
