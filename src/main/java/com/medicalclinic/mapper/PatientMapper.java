package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.PatientDTO;
import com.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient source);
}
