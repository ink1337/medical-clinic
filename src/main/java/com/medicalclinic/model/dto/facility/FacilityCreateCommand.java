package com.medicalclinic.model.dto.facility;

import com.medicalclinic.model.dto.doctor.DoctorSimpleDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class FacilityCreateCommand {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
    private Set<DoctorSimpleDTO> doctors;
}