package com.medicalclinic.repository;

import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select count(v) = 0 from VISITS v where v.startTime <=:endTime and v.endTime >= :startTime and v.doctor=:doctor")
    boolean dateIsAvailable(OffsetDateTime startTime, OffsetDateTime endTime, Doctor doctor);

    @Modifying
    @Query("update VISITS v set v.patient =null where v.patient.id = :patient")
    void detachPatientFromVisits(@Param("patient") Patient patient);

    @Modifying
    @Query("update VISITS v set v.doctor =null where v.doctor = :doctor")
    void detachDoctorFromVisits(@Param("doctor") Doctor doctor);
}
