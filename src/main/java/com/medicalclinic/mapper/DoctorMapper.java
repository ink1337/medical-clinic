package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.DoctorDTO;
import com.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "facilities", expression = "java(new java.util.HashSet<>())")
    DoctorDTO toDTO(Doctor source);

//    @Named("mapFacilityNames")
//    default Set<String> mapFacilityNames(Set<Facility> facilities) {
//        Set<String> names = new HashSet<>();
//        for (Facility facility : facilities) {
//            names.add(facility.getName());
//        }
//        return names;
//
//    }
}
