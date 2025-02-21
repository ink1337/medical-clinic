package com.medicalclinic.repository;

import com.medicalclinic.exception.ProcessingPatientException;
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

    public Optional<Patient> findPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .map(patient -> patient.toBuilder().build());
    }

    public boolean updateByEmail(Patient updatedPatient, String referencedEmail) {
            Patient existingPatient = patients.stream()
                    .filter(patient -> patient.getEmail().equals(referencedEmail))
                    .findFirst()
                    .orElseThrow(() -> new ProcessingPatientException("Patient with email " + referencedEmail + " not found"));

            existingPatient.setPassword(Optional.ofNullable(updatedPatient.getPassword()).orElse(existingPatient.getPassword()));
            existingPatient.setFirstName(Optional.ofNullable(updatedPatient.getFirstName()).orElse(existingPatient.getFirstName()));
            existingPatient.setLastName(Optional.ofNullable(updatedPatient.getLastName()).orElse(existingPatient.getLastName()));
            existingPatient.setBirthday(Optional.ofNullable(updatedPatient.getBirthday()).orElse(existingPatient.getBirthday()));
            existingPatient.setIdCardNo(Optional.ofNullable(updatedPatient.getIdCardNo()).orElse(existingPatient.getIdCardNo()));
            existingPatient.setEmail(Optional.ofNullable(updatedPatient.getEmail()).orElse(existingPatient.getEmail()));
            existingPatient.setPhoneNumber(Optional.ofNullable(updatedPatient.getPhoneNumber()).orElse(existingPatient.getPhoneNumber()));
            return true;
    }


    public void persist(Patient patient) {
        if(findPatientByEmail(patient.getEmail()).isPresent()){
            throw new ProcessingPatientException("Cannot add this Patient beacuse one already exist with this mail: " + patient.getEmail());
        }
        patients.add(patient);
    }

    public boolean deletePatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .map(patients::remove)
                .orElseThrow(() -> new ProcessingPatientException("Patient with email " + email + " not found"));
    }
}