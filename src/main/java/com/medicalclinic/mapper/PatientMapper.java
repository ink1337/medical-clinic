package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.patient.PatientOutDTO;
import com.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientOutDTO toDTO(Patient source);
}
