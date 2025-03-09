package com.medicalclinic.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String idCardNo;
    private String phoneNumber;

    public void update(Patient patientData) {
        email = patientData.getEmail();
        password = patientData.getPassword();
        idCardNo = patientData.getIdCardNo();
        firstName = patientData.getFirstName();
        lastName = patientData.getLastName();
        phoneNumber = patientData.getPhoneNumber();
        birthday = patientData.getBirthday();
    }
}

