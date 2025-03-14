package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.patient.PatientInDTO;
import com.medicalclinic.model.dto.patient.PatientOutDTO;
import com.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientOutDTO toDTO(Patient source);
    Set<PatientOutDTO> toDTOs(List<Patient> source);

    Patient toEnity(PatientInDTO patient);
}
