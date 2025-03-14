package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.doctor.DoctorInDTO;
import com.medicalclinic.model.dto.doctor.DoctorOutDTO;
import com.medicalclinic.model.dto.doctor.DoctorSimpleDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "facilities", source = "facilities", qualifiedByName = "mapFacilityIds")
    DoctorOutDTO toDTO(Doctor source);

    Set<DoctorOutDTO> toDTOs(List<Doctor> source);

    Doctor toEntity(DoctorInDTO source);

    Doctor toEntity(DoctorSimpleDTO source);

    Set<Doctor> toEntity(Set<DoctorSimpleDTO> source);

    @Named("mapFacilityIds")
    default Set<Long> mapFacilityIds(Set<Facility> facilities) {
        return facilities.stream()
                .map(Facility::getId)
                .collect(Collectors.toSet());
    }
}
