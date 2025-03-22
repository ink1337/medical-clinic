package com.medicalclinic.model.entity;

import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import jakarta.persistence.*;
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
@AllArgsConstructor
@NoArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String city;

    private String street;

    private String buildingNumber;

    private String postCode;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "facilities")
    private Set<Doctor> doctors = new HashSet<>();

    public void update(FacilityCreateCommand updatedFacility) {
        this.name = updatedFacility.getName();
        this.city = updatedFacility.getCity();
        this.street = updatedFacility.getStreet();
        this.buildingNumber = updatedFacility.getBuildingNumber();
        this.postCode = updatedFacility.getPostCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facility other))
            return false;
        return name.equals((other.getName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", doctors=" + doctors.stream().map(Doctor::getId) +
                '}';
    }
}

