package com.medicalclinic.repository;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PatientRepository {

    private final List<Patient> patients;

    public List<Patient> listAll() {
        return Collections.unmodifiableList(patients);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return findPatientByEmailInternal(email)
                .map(patient -> patient.toBuilder().build());
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

    private Optional<Patient> findPatientByEmailInternal(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }
}