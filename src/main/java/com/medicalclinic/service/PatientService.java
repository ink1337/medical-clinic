package com.medicalclinic.service;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

import java.util.List;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import com.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.listAll();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    public void addPatient(Patient patient) {
        patientRepository.persist(patient);
    }

    public boolean deletePatientByEmail(String email) {
        return patientRepository.deletePatientByEmail(email);
    }

    public boolean updatePatientByEmail(Patient newPatient, String referencedEmail) {
        return patientRepository.updateByEmail(newPatient, referencedEmail);
    }

    public boolean changePasswordByEmail(String email, String password) {
        var patient = Patient.builder()
                .password(password)
                .build();
        return patientRepository.updateByEmail(patient, email);
    }
}
