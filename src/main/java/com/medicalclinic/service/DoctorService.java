package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.DoctorMapper;
import com.medicalclinic.model.dto.DoctorDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.repository.DoctorRepository;
import com.medicalclinic.repository.FacilityRepository;
import com.medicalclinic.validator.DoctorValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@AllArgsConstructor
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
    public void add(Doctor patient) {
        doctorValidator.validateDoctorForPersist(patient);
        doctorRepository.save(patient);
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
    public DoctorDTO updateByEmail(Doctor newPatient, String referencedEmail) {
        var entity = doctorValidator.validateAndGetDoctorToUpdate(newPatient, referencedEmail);
        entity.update(newPatient);
        doctorRepository.save(entity);
        return doctorMapper.toDTO(entity);
    }

    @Transactional
    public void addFacility(String email, String facilityName) {
        var entity = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("doctor.not_found", email)));
        var facilityEntity = facilityRepository.findByName(facilityName)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("facility.not_found", facilityName)));
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
