package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.FacilityDTO;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDTO toDTO(Facility source);
}
