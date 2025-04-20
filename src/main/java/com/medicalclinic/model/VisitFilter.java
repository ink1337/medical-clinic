package com.medicalclinic.model;

import java.time.OffsetDateTime;

public record VisitFilter(
        Long visitId,
        Long doctorId,
        String doctorSpecialization,
        Long patientId,
        Boolean onlyAvailable,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
