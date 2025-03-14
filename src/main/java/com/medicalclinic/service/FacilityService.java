package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.DoctorMapper;
import com.medicalclinic.mapper.FacilityMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.facility.FacilityInDTO;
import com.medicalclinic.model.dto.facility.FacilityOutDTO;
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

    public PageableDataDTO<FacilityOutDTO> getAll(Pageable pageable) {
        var result = facilityRepository.findAll(pageable);
        return PageableDataDTO.<FacilityOutDTO>builder()
                .data(facilityMapper.toDTOs(result.getContent()))
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .currentPage(pageable.getPageNumber())
                .build();
    }

    public FacilityOutDTO getByName(String email) {
        return facilityRepository.findByName(email)
                .map(facilityMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(FacilityInDTO facility) {
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
    public boolean deleteByName(String email) {
        var patientOptional = facilityRepository.findByName(email);
        if (patientOptional.isPresent()) {
            facilityRepository.delete(patientOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    public FacilityOutDTO updateByName(FacilityInDTO data, String referencedName) {
        var entity = facilityValidator.validateAndGetFacilityToUpdate(data, referencedName);
        entity.update(data);
        facilityRepository.save(entity);
        return facilityMapper.toDTO(entity);
    }
}
