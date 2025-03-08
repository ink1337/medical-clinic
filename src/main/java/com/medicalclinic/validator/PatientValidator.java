package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Component
@AllArgsConstructor
public class PatientValidator {
    private final PatientRepository patientRepository;

    public Patient validateAndGetPatientToUpdate(Patient newPatient, String referencedEmail) {
        var newPatientEmail = newPatient.getEmail();
        var existingPatient = patientRepository.findByEmail(referencedEmail)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", referencedEmail)));

        if (!Objects.equals(referencedEmail, newPatientEmail)) {
            validateIfEmailIsValid(newPatientEmail);
        }
        if (!Objects.equals(newPatient.getIdCardNo(), existingPatient.getIdCardNo())) {
            throw new ProcessingPatientException(getMessage("patient.cannot_change_idCardNo", referencedEmail));
        }
        return existingPatient;
    }


    public void validatePatientForPersist(Patient patient) {
        checkIfPatientWithEmailExists(patient.getEmail());
        validateNoneNullFields(patient);
    }

    private void validateIfEmailIsValid(String email) {
        if (patientRepository.findByEmail(email).isPresent()) {
            throw new ProcessingPatientException(getMessage("patient.already_exists", email));
        }
    }

    private void validateNoneNullFields(Patient patient) {
        if (patient.getEmail() == null
                || patient.getPassword() == null
                || patient.getIdCardNo() == null
                || patient.getFirstName() == null
                || patient.getLastName() == null
                || patient.getPhoneNumber() == null
                || patient.getBirthday() == null) {
            throw new ProcessingPatientException(getMessage("patient.all_field_must_be_set", patient.getEmail()));
        }
    }

    public void checkIfPatientWithEmailExists(String email) {
        if (patientRepository.findByEmail(email).isPresent()) {
            throw new ProcessingPatientException(getMessage("patient.already_exists", email));
        }
    }
}