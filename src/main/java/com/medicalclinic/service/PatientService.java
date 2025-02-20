package com.medicalclinic.service;

import com.medicalclinic.model.Patient;
import com.medicalclinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.listAll();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email).orElse(null);
    }

    public void addNewPatient(Patient patient) {
        patientRepository.persist(patient);
    }

    public boolean deletePatientByEmail(String email) {
        return patientRepository.deletePatientByEmail(email);
    }

    public boolean updatePatientByEmail(Patient newPatient, String referencedEmail) {
        return patientRepository.updateByEmail(newPatient, referencedEmail);
    }
}
