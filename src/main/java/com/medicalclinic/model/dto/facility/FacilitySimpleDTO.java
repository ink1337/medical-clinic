package com.medicalclinic.model.dto.facility;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FacilitySimpleDTO {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
}