package com.medicalclinic.model.dto.facility;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FacilityDTO {
    private String name;
    private String city;
    private String street;
    private String buildingNumber;
    private String postCode;
}