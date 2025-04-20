package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingDoctorException;
import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.exception.ProcessingVisitException;
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
            throw new ProcessingVisitException(getMessage("visit.not_available"));
        }
    }

    private void validateIfDateAvailable(VisitCreateCommand visit, Doctor doctor) {
        var startTime = visit.getStartTime();
        var endTime = visit.getEndTime();
        if (startTime.isBefore(OffsetDateTime.now()) ||
                startTime.getMinute() % 15 != 0 || endTime.getMinute() % 15 != 0 ||
                (endTime.isBefore(startTime) || endTime.isEqual(startTime))) {
            throw new ProcessingVisitException(getMessage("visit.date_is_incorrect", startTime, endTime));
        }
        if (!visitRepository.dateIsAvailable(visit.getStartTime(), visit.getEndTime(), doctor)) {
            throw new ProcessingVisitException(getMessage("visit.date_is_not_available", startTime, endTime));
        }
    }

    public void validateTime(OffsetDateTime startTime, OffsetDateTime endTime) {
        if (endTime != null && endTime.isBefore(OffsetDateTime.now())) {
            throw new ProcessingVisitException(getMessage("visit.date_is_incorrect", startTime, endTime));
        }
        if (startTime != null && endTime != null && endTime.isBefore(startTime)) {
            throw new ProcessingVisitException(getMessage("visit.date_is_incorrect", startTime, endTime));
        }
    }

    private void validateNonNullFields(VisitCreateCommand visit) {
        if (visit.getDoctorId() == null ||
                visit.getStartTime() == null ||
                visit.getEndTime() == null) {
            throw new ProcessingVisitException(getMessage("visit.all_field_must_be_set"));
        }
    }

}
