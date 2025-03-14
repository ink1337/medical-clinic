package com.medicalclinic.model.dto.facility;

import lombok.Builder;
import lombok.Getter;

@Builder()
@Getter
public class FacilityOutDTO {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
}