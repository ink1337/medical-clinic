package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingDoctorException;
import com.medicalclinic.model.dto.doctor.DoctorCreateCommand;
import com.medicalclinic.model.dto.doctor.DoctorSimpleDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Component
@RequiredArgsConstructor
public class DoctorValidator {
    private final DoctorRepository doctorRepository;

    public Doctor validateAndGetDoctorToUpdate(DoctorCreateCommand newDoctor, String referencedEmail) {
        var newDoctorEmail = newDoctor.getEmail();
        var existingDoctor = doctorRepository.findByEmail(referencedEmail)
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("doctor.not_found", referencedEmail)));

        if (!Objects.equals(referencedEmail, newDoctorEmail)) {
            validateIfEmailIsValid(newDoctorEmail);
        }
        return existingDoctor;
    }

    public void validateDoctorForPersist(DoctorCreateCommand doctor) {
        checkIfDoctorWithEmailExists(doctor.getEmail());
        validateNoneNullFields(doctor);
    }

    public void checkIfDoctorWithEmailExists(String email) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new ProcessingDoctorException(getMessage("doctor.already_exists", email));
        }
    }

    public void validateSimpleDoctors(Set<DoctorSimpleDTO> doctors) {
        doctors.forEach(this::validateNoneNullFields);
    }

    private void validateIfEmailIsValid(String email) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new ProcessingDoctorException(getMessage("doctor.already_exists", email));
        }
    }

    private void validateNoneNullFields(DoctorCreateCommand doctor) {
        if (doctor.getEmail() == null
                || doctor.getPassword() == null
                || doctor.getFirstName() == null
                || doctor.getLastName() == null
                || doctor.getSpeciality() == null) {
            throw new ProcessingDoctorException(getMessage("doctor.all_field_must_be_set", doctor.getEmail()));
        }
    }

    private void validateNoneNullFields(DoctorSimpleDTO doctor) {
        if (doctor.getEmail() == null
                || doctor.getPassword() == null
                || doctor.getFirstName() == null
                || doctor.getLastName() == null
                || doctor.getSpeciality() == null) {
            throw new ProcessingDoctorException(getMessage("doctor.all_field_must_be_set", doctor.getEmail()));
        }
    }
}