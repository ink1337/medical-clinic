package com.medicalclinic.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Data
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String speciality;

    @ManyToMany()
    @JoinTable(
            name = "doctor_facility",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private Set<Facility> facilities = new HashSet<>();

    public void update(Doctor updatedDoctor) {
        this.email = updatedDoctor.getEmail();
        this.password = updatedDoctor.getPassword();
        this.firstName = updatedDoctor.getFirstName();
        this.lastName = updatedDoctor.getLastName();
        this.speciality = updatedDoctor.getSpeciality();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor other))
            return false;
        return id != null && id.equals((other.getId()));
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}