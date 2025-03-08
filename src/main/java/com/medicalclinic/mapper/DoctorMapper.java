package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.DoctorDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "facilities", source = "facilities", qualifiedByName = "mapFacilityNames")
    DoctorDTO toDTO(Doctor source);

    @Named("mapFacilityNames")
    default Set<String> mapFacilityNames(Set<Facility> facilities) {
        return facilities.stream()
                .map(Facility::getName)
                .collect(Collectors.toSet());
    }
}
