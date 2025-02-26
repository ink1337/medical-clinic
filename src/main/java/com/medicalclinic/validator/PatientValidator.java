package com.medicalclinic.validator;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientValidator {

    public void validatePatientForUpdate(Patient patient, String referencedEmail) {
        String idCardNo = patient.getIdCardNo();
        if (idCardNo != null && !idCardNo.isBlank()) {
            throw new ProcessingPatientException(getMessage("patient.cannot_change_idCardNo", referencedEmail));
        }

    }
}