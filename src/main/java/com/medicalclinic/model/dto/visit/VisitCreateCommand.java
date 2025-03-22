package com.medicalclinic.model.dto.visit;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class VisitCreateCommand {
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    Long doctorId;
}
