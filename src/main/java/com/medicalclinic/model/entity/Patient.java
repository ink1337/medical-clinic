package com.medicalclinic.model.entity;

import com.medicalclinic.model.dto.patient.PatientCreateCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String idCardNo;

    private String phoneNumber;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    List<Visit> visits;


    public void update(PatientCreateCommand patientData) {
        email = patientData.getEmail();
        password = patientData.getPassword();
        idCardNo = patientData.getIdCardNo();
        firstName = patientData.getFirstName();
        lastName = patientData.getLastName();
        phoneNumber = patientData.getPhoneNumber();
        birthday = patientData.getBirthday();
    }
}

