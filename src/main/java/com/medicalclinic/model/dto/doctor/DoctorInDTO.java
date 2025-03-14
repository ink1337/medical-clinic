package com.medicalclinic.model.dto.doctor;

import com.medicalclinic.model.dto.facility.FacilitySimpleDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder()
@Getter
public class DoctorInDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String speciality;
    private Set<FacilitySimpleDTO> facilities;
}
