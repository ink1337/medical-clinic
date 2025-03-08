package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingDoctorException;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Component
@AllArgsConstructor
public class DoctorValidator {
    private final DoctorRepository doctorRepository;

    public Doctor validateAndGetDoctorToUpdate(Doctor newDoctor, String referencedEmail) {
        var newDoctorEmail = newDoctor.getEmail();
        var existingDoctor = doctorRepository.findByEmail(referencedEmail)
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("doctor.not_found", referencedEmail)));

        if (!Objects.equals(referencedEmail, newDoctorEmail)) {
            validateIfEmailIsValid(newDoctorEmail);
        }
        return existingDoctor;
    }

    public void validateDoctorForPersist(Doctor doctor) {
        checkIfDoctorWithEmailExists(doctor.getEmail());
        validateNoneNullFields(doctor);
    }

    private void validateIfEmailIsValid(String email) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new ProcessingDoctorException(getMessage("doctor.already_exists", email));
        }
    }

    private void validateNoneNullFields(Doctor doctor) {
        if (doctor.getEmail() == null
                || doctor.getPassword() == null
                || doctor.getFirstName() == null
                || doctor.getLastName() == null
                || doctor.getSpeciality() == null) {
            throw new ProcessingDoctorException(getMessage("doctor.all_field_must_be_set", doctor.getEmail()));
        }
    }

    public void checkIfDoctorWithEmailExists(String email) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new ProcessingDoctorException(getMessage("doctor.already_exists", email));
        }
    }
}