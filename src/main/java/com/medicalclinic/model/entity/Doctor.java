package com.medicalclinic.model.entity;

import com.medicalclinic.model.dto.doctor.DoctorCreateCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String speciality;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "doctor_facility",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private Set<Facility> facilities = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    List<Visit> visits;

    public void update(DoctorCreateCommand updatedDoctor) {
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

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", facilities=" + facilities.stream().map(Facility::getId) +
                '}';
    }
}