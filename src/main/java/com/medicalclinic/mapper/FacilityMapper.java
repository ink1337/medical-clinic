package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import com.medicalclinic.model.dto.facility.FacilityDTO;
import com.medicalclinic.model.dto.facility.FacilitySimpleDTO;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDTO toDTO(Facility source);

    Set<FacilityDTO> toDTOs(List<Facility> source);

    Facility toEntity(FacilityCreateCommand source);

    Facility toEntity(FacilitySimpleDTO source);

    Set<Facility> toEntity(List<FacilitySimpleDTO> source);
}
