package com.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Entity(name = "VISITS")
@Setter
@Getter
@ToString
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @ManyToOne
    @JoinColumn
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit visit)) {
            return false;
        }
        return id != null && id.equals(visit.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
