package com.medicalclinic.validator;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

import java.util.Objects;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import com.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientValidator {
    private PatientRepository patientRepository;

    public void validatePatientForUpdate(Patient newPatient, Patient existingPatient) {
        String idCardNo = newPatient.getIdCardNo();
        if (!Objects.equals(idCardNo, existingPatient.getIdCardNo())) {
            throw new ProcessingPatientException(getMessage("patient.cannot_change_idCardNo", existingPatient.getEmail()));
        }
    }

    public void validatePatientForPersist(Patient patient) {
        validateIfPatientAlreadyExists(patient);
        validateNoneNullFields(patient);
    }

    private void validateIfPatientAlreadyExists(Patient patient) {
        if (patientRepository.findPatientByEmail(patient.getEmail()).isPresent()) {
            throw new ProcessingPatientException(getMessage("patient.already_exists", patient.getEmail()));
        }
    }

    private void validateNoneNullFields(Patient patient) {
        if(patient.getEmail() == null
                || patient.getPassword() == null
                || patient.getIdCardNo() == null
                || patient.getFirstName() == null
                || patient.getLastName() == null
                || patient.getPhoneNumber() == null
                || patient.getBirthday() == null){
            throw new ProcessingPatientException(getMessage("patient.all_field_must_be_set", patient.getEmail()));
        }
    }
}