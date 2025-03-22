package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingDoctorException;
import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.model.dto.visit.VisitCreateCommand;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Visit;
import com.medicalclinic.repository.DoctorRepository;
import com.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Component
@RequiredArgsConstructor
public class VisitValidator {
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;

    public void validateForPersist(VisitCreateCommand visit) {
        validateNonNullFields(visit);
        var doctor = doctorRepository.findById(visit.getDoctorId())
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("doctor.id_not_found", visit.getDoctorId())));
        validateIfDateAvailable(visit, doctor);
    }

    public void validateVisitAvailability(Visit visit) {
        if (visit.getStartTime().isBefore(OffsetDateTime.now()) ||
                visit.getPatient() != null) {
            throw new ProcessingPatientException(getMessage("visit.not_available"));
        }
    }

    private void validateIfDateAvailable(VisitCreateCommand visit, Doctor doctor) {
        var startTime = visit.getStartTime();
        var endTime = visit.getEndTime();
        if (startTime.isBefore(OffsetDateTime.now()) ||
                startTime.getMinute() % 15 != 0 || endTime.getMinute() % 15 != 0 ||
                (endTime.isBefore(startTime) || endTime.isEqual(startTime))) {
            throw new ProcessingPatientException(getMessage("visit.date_is_incorrect"));
        }
        if (!visitRepository.dateIsAvailable(visit.getStartTime(), visit.getEndTime(), doctor)) {
            throw new ProcessingPatientException(getMessage("visit.date_is_not_available"));
        }
    }

    private void validateNonNullFields(VisitCreateCommand visit) {
        if (visit.getDoctorId() == null ||
                visit.getStartTime() == null ||
                visit.getEndTime() == null) {
            throw new ProcessingPatientException(getMessage("visit.all_field_must_be_set"));
        }
    }

}
