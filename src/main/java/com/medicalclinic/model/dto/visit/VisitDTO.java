package com.medicalclinic.model.dto.visit;

import com.medicalclinic.model.dto.doctor.DoctorSimpleDTO;
import com.medicalclinic.model.dto.patient.PatientDTO;

import java.time.OffsetDateTime;

public record VisitDTO(
        Long id,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        DoctorSimpleDTO doctor,
        PatientDTO patient
) {
}
