package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.doctor.DoctorCommandDTO;
import com.medicalclinic.model.dto.doctor.DoctorDTO;
import com.medicalclinic.model.dto.doctor.DoctorSimpleDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "facilities", source = "facilities", qualifiedByName = "mapFacilityIds")
    DoctorDTO toDTO(Doctor source);

    Set<DoctorDTO> toDTOs(List<Doctor> source);

    Doctor toEntity(DoctorCommandDTO source);

    Doctor toEntity(DoctorSimpleDTO source);

    Set<Doctor> toEntity(Set<DoctorSimpleDTO> source);

    @Named("mapFacilityIds")
    default Set<Long> mapFacilityIds(Set<Facility> facilities) {
        return Optional.ofNullable(facilities)
                .orElse(Collections.emptySet())
                .stream()
                .map(Facility::getId)
                .collect(Collectors.toSet());
    }
}
