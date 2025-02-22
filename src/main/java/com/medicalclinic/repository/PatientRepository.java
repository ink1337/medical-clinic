package com.medicalclinic.repository;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

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
        return findPatientByEmailInternal(email)
                .map(patient -> patient.toBuilder().build());
    }

    private Optional<Patient> findPatientByEmailInternal(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public boolean updateByEmail(Patient updatedPatient, String referencedEmail) {
        Patient existingPatient = findPatientByEmailInternal(referencedEmail)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", referencedEmail)));
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
        if (findPatientByEmail(patient.getEmail()).isPresent()) {
            throw new ProcessingPatientException(getMessage("patient.already_exists", patient.getEmail()));
        }
        patients.add(patient);
    }

    public boolean deletePatientByEmail(String email) {
        return findPatientByEmailInternal(email)
                .map(patients::remove)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }
}