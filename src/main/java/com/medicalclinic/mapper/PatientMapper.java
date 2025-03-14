package com.medicalclinic.mapper;

import com.medicalclinic.model.dto.patient.PatientCommandDTO;
import com.medicalclinic.model.dto.patient.PatientDTO;
import com.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient source);
    Set<PatientDTO> toDTOs(List<Patient> source);

    Patient toEnity(PatientCommandDTO patient);
}
