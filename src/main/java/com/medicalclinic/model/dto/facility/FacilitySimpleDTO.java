package com.medicalclinic.model.dto.facility;

import lombok.Builder;
import lombok.Getter;

@Builder()
@Getter
public class FacilitySimpleDTO {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
}