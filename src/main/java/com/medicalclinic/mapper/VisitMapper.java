package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.visit.VisitCreateCommand;
import com.medicalclinic.model.dto.visit.VisitDTO;
import com.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {DoctorMapper.class, PatientMapper.class})
public interface VisitMapper {

    VisitDTO toDTO(Visit source);

    Set<VisitDTO> toDTOs(List<Visit> source);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Visit toEntity(VisitCreateCommand visit);
}
