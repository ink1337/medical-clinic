package com.medicalclinic.model.entity;

import com.medicalclinic.model.dto.patient.PatientCreateCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Patient patient)) return false;

        return id != null && Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

