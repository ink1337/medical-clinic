package com.medicalclinic.model.dto.doctor;

import com.medicalclinic.model.dto.facility.FacilitySimpleDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DoctorCreateCommand {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<FacilitySimpleDTO> facilities;
}
