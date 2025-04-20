package com.medicalclinic.service.common;

import com.medicalclinic.model.VisitFilter;
import com.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.domain.Specification;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VisitSpecification {
    public static Specification<Visit> constructVisitSpecification(VisitFilter visitFilter) {
        List<Specification<Visit>> specs = new ArrayList<>();

        Optional.ofNullable(visitFilter.visitId())
                .ifPresent(id -> specs.add(visitIdEquals(id)));

        Optional.ofNullable(visitFilter.doctorId())
                .ifPresent(id -> specs.add(doctorIdEquals(id)));

        Optional.ofNullable(visitFilter.doctorSpecialization())
                .ifPresent(spec -> specs.add(doctorSpecializationEquals(spec)));

        Optional.ofNullable(visitFilter.patientId())
                .ifPresent(id -> specs.add(patientIdEquals(id)));

        Optional.ofNullable(visitFilter.onlyAvailable())
                .filter(Boolean::booleanValue)
                .ifPresent(val -> specs.add(visitIsAvailable(OffsetDateTime.now())));

        Optional.ofNullable(visitFilter.startTime())
                .ifPresent(start -> specs.add(startTimeAfterOrEquals(start)));

        Optional.ofNullable(visitFilter.endTime())
                .ifPresent(end -> specs.add(endTimeBeforeOrEquals(end)));

        return specs.stream()
                .reduce(Specification::and)
                .orElse(null);
    }

    private static Specification<Visit> visitIdEquals(Long visitId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), visitId);
    }

    private static Specification<Visit> doctorIdEquals(Long doctorId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("doctor").get("id"), doctorId);
    }

    private static Specification<Visit> doctorSpecializationEquals(String specialization) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("doctor").get("specialization"), specialization);
    }

    private static Specification<Visit> patientIdEquals(Long patientId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("patient").get("id"), patientId);
    }

    private static Specification<Visit> visitIsAvailable(OffsetDateTime currentTime) {
        Specification<Visit> patientIsNull = (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("patient"));
        Specification<Visit> visitIsInTheFuture = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), currentTime);
        return Specification.where(patientIsNull).and(visitIsInTheFuture);
    }

    private static Specification<Visit> startTimeAfterOrEquals(OffsetDateTime startTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime);
    }

    private static Specification<Visit> endTimeBeforeOrEquals(OffsetDateTime endTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime);
    }
}
