package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import com.medicalclinic.repository.PatientRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Service
public class PatientService {
    private final MessageSource messageSource;
    private final PatientRepository patientRepository;

    public PatientService(MessageSource messageSource, PatientRepository patientRepository) {
        this.messageSource = messageSource;
        this.patientRepository = patientRepository;
    }

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
}
