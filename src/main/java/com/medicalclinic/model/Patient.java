package com.medicalclinic.model;

import lombok.Data;

import java.util.Date;

@Data
public class Patient {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long idCardNo;
}
