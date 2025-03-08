package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.PatientMapper;
import com.medicalclinic.model.dto.PatientDTO;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.repository.PatientRepository;
import com.medicalclinic.validator.PatientValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@AllArgsConstructor
@Service
public class PatientService {
    private final PatientValidator patientValidator;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .toList();
    }

    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void addPatient(Patient patient) {
        patientValidator.validatePatientForPersist(patient);
        patientRepository.save(patient);
    }

    @Transactional
    public boolean deletePatientByEmail(String email) {
        var patientOptional = patientRepository.findByEmail(email);
        if (patientOptional.isPresent()) {
            patientRepository.delete(patientOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    public PatientDTO updatePatientByEmail(Patient newPatient, String referencedEmail) {
        var patientToUpdate = patientValidator.validateAndGetPatientToUpdate(newPatient, referencedEmail);
        patientToUpdate.update(newPatient);
        patientRepository.save(patientToUpdate);
        return patientMapper.toDTO(patientToUpdate);
    }

    @Transactional
    public boolean changePasswordByEmail(String email, String password) {
        var existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
        existingPatient.setPassword(password);
        return true;
    }
}
