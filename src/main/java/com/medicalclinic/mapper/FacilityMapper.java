package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.facility.FacilityInDTO;
import com.medicalclinic.model.dto.facility.FacilityOutDTO;
import com.medicalclinic.model.dto.facility.FacilitySimpleDTO;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityOutDTO toDTO(Facility source);

    Set<FacilityOutDTO> toDTOs(List<Facility> source);

    Facility toEntity(FacilityInDTO source);

    Facility toEntity(FacilitySimpleDTO source);

    Set<Facility> toEntity(Set<FacilitySimpleDTO> source);
}
