package com.medicalclinic.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder()
@Getter
public class FacilityDTO {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
}