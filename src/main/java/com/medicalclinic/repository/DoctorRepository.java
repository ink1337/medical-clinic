package com.medicalclinic.repository;

import com.medicalclinic.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.visits WHERE d.id = :id")
    Optional<Doctor> findByIdWithVisits(@Param("id") Long id);
}
