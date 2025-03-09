package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.DoctorMapper;
import com.medicalclinic.model.dto.DoctorDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.repository.DoctorRepository;
import com.medicalclinic.repository.FacilityRepository;
import com.medicalclinic.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorValidator doctorValidator;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final FacilityRepository facilityRepository;

    public List<DoctorDTO> getAll() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    public DoctorDTO getByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(Doctor doctor) {
        doctorValidator.validateDoctorForPersist(doctor);
        doctorRepository.save(doctor);
    }

    @Transactional
    public boolean deleteByEmail(String email) {
        var patientOptional = doctorRepository.findByEmail(email);
        if (patientOptional.isPresent()) {
            doctorRepository.delete(patientOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    public DoctorDTO updateByEmail(Doctor data, String referencedEmail) {
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
