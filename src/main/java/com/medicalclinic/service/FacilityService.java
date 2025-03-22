package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.DoctorMapper;
import com.medicalclinic.mapper.FacilityMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import com.medicalclinic.model.dto.facility.FacilityDTO;
import com.medicalclinic.repository.FacilityRepository;
import com.medicalclinic.validator.DoctorValidator;
import com.medicalclinic.validator.FacilityValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@RequiredArgsConstructor
@Service
public class FacilityService {
    private final FacilityValidator facilityValidator;
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final DoctorValidator doctorValidator;
    private final DoctorMapper doctorMapper;

    public PageableDataDTO<FacilityDTO> getAll(Pageable pageable) {
        var result = facilityRepository.findAll(pageable);
        return PageableDataDTO.from(facilityMapper.toDTOs(result.getContent()), result, pageable);

    }

    public FacilityDTO getByName(String email) {
        return facilityRepository.findByName(email)
                .map(facilityMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("facility.not_found", email)));
    }

    @Transactional
    public void add(FacilityCreateCommand facility) {
        var doctors = facility.getDoctors();
        facilityValidator.validateFacilityForPersist(facility);
        var entity = facilityMapper.toEntity(facility);
        if (!doctors.isEmpty()) {
            doctorValidator.validateSimpleDoctors(doctors);
            entity.setDoctors(doctorMapper.toEntity(doctors));
        }
        facilityRepository.save(entity);
    }

    @Transactional
    public void deleteByName(String name) {
        var facility = facilityRepository.findByName(name)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("facility.not_found", name)));
        facilityRepository.delete(facility);
    }

    @Transactional
    public FacilityDTO updateByName(FacilityCreateCommand data, String referencedName) {
        var entity = facilityValidator.validateAndGetFacilityToUpdate(data, referencedName);
        entity.update(data);
        facilityRepository.save(entity);
        return facilityMapper.toDTO(entity);
    }
}
