package com.medicalclinic.repository;

import com.medicalclinic.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.visits WHERE p.id = :id")
    Optional<Patient> findByIdWithVisits(@Param("id") Long id);
}
