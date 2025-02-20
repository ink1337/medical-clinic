package com.medicalclinic.repository;

import com.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final List<Patient> patients;

    public PatientRepository() {
        this.patients = new ArrayList<>();
    }

    public List<Patient> listAll() {
        return Collections.unmodifiableList(patients);
    }

    public Optional<Patient> findPatientByEmail(String email) {//to do jak lista pacjentów pusta
        return patients.stream().filter(patient -> patient !=null && patient.getEmail().equals(email)).findFirst().map(patient -> patient.toBuilder().build());
    }

    public boolean updateByEmail(Patient updatedPatient, String referencedEmail) {
        return patients.stream()
                .filter(p -> p!=null&& p.getEmail().equals(referencedEmail))
                .findFirst()
                .map(existingPatient -> {
                    existingPatient.setPassword(Optional.ofNullable(updatedPatient.getPassword()).orElse(existingPatient.getPassword()));
                    existingPatient.setFirstName(Optional.ofNullable(updatedPatient.getFirstName()).orElse(existingPatient.getFirstName()));
                    existingPatient.setLastName(Optional.ofNullable(updatedPatient.getLastName()).orElse(existingPatient.getLastName()));
                    existingPatient.setBirthday(Optional.ofNullable(updatedPatient.getBirthday()).orElse(existingPatient.getBirthday()));
                    existingPatient.setIdCardNo(Optional.ofNullable(updatedPatient.getIdCardNo()).orElse(existingPatient.getIdCardNo()));
                    existingPatient.setEmail(Optional.ofNullable(updatedPatient.getEmail()).orElse(existingPatient.getEmail()));
                    existingPatient.setPhoneNumber(Optional.ofNullable(updatedPatient.getPhoneNumber()).orElse(existingPatient.getPhoneNumber()));
                    return true;
                })
                .orElse(false);
    }


    public void persist(Patient patient) {
        patients.add(patient);
    }

    public boolean deletePatient(Patient patient) {
        return patients.remove(patient);
    }
}