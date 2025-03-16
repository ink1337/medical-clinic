package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.DoctorMapper;
import com.medicalclinic.mapper.FacilityMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.doctor.DoctorDTO;
import com.medicalclinic.model.dto.doctor.DoctorCommandDTO;
import com.medicalclinic.repository.DoctorRepository;
import com.medicalclinic.repository.FacilityRepository;
import com.medicalclinic.validator.DoctorValidator;
import com.medicalclinic.validator.FacilityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorValidator doctorValidator;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final FacilityValidator facilityValidator;

    public PageableDataDTO<DoctorDTO> getAll(Pageable pageable) {
        var result = doctorRepository.findAll(pageable);
        return PageableDataDTO.from(doctorMapper.toDTOs(result.getContent()), result, pageable);
    }


    public DoctorDTO getByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(DoctorCommandDTO doctor) {
        var facilities = doctor.getFacilities();
        doctorValidator.validateDoctorForPersist(doctor);
        var entity = doctorMapper.toEntity(doctor);
        if (!facilities.isEmpty()) {
            facilityValidator.validateSimpleFacilities(facilities);
            entity.setFacilities(facilityMapper.toEntity(facilities));
        }
        doctorRepository.save(entity);
    }

    @Transactional
    public void deleteByEmail(String email) {
        var doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("doctor.not_found", email)));
        doctorRepository.delete(doctor);
    }

    @Transactional
    public DoctorDTO updateByEmail(DoctorCommandDTO data, String referencedEmail) {
        var entity = doctorValidator.validateAndGetDoctorToUpdate(data, referencedEmail);
        entity.update(data);
        doctorRepository.save(entity);
        return doctorMapper.toDTO(entity);
    }

    @Transactional
    public void addFacility(String email, Long id) {
        var entity = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("doctor.not_found", email)));
        var facilityEntity = facilityRepository.findById(id)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("facility.not_found", id)));
        entity.getFacilities().add(facilityEntity);

    }

    @Transactional
    public boolean changePasswordByEmail(String email, String password) {
        var existingPatient = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("doctor.not_found", email)));
        existingPatient.setPassword(password);
        return true;
    }
}
