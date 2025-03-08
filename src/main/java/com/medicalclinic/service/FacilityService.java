package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.FacilityMapper;
import com.medicalclinic.model.dto.FacilityDTO;
import com.medicalclinic.model.entity.Facility;
import com.medicalclinic.repository.FacilityRepository;
import com.medicalclinic.validator.FacilityValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@AllArgsConstructor
@Service
public class FacilityService {
    private final FacilityValidator doctorValidator;
    private final FacilityRepository doctorRepository;
    private final FacilityMapper doctorMapper;

    public List<FacilityDTO> getAll() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    public FacilityDTO getByName(String email) {
        return doctorRepository.findByName(email)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(Facility patient) {
        doctorValidator.validateFacilityForPersist(patient);
        doctorRepository.save(patient);
    }

    @Transactional
    public boolean deleteByName(String email) {
        var patientOptional = doctorRepository.findByName(email);
        if (patientOptional.isPresent()) {
            doctorRepository.delete(patientOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    public FacilityDTO updateByName(Facility data, String referencedName) {
        var entity = doctorValidator.validateAndGetFacilityToUpdate(data, referencedName);
        entity.update(data);
        doctorRepository.save(entity);
        return doctorMapper.toDTO(entity);
    }
}
